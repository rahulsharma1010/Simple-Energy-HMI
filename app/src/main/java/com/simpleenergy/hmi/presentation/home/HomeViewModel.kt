
package com.simpleenergy.hmi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simpleenergy.hmi.core.ElapsedClock
import com.simpleenergy.hmi.core.VehicleDataValidator
import com.simpleenergy.hmi.domain.usecase.ObserveVehicleDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeVehicleDataUseCase: ObserveVehicleDataUseCase,
    private val validator: VehicleDataValidator,
    private val clock: ElapsedClock
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var lastEmissionTime: Long? = null

    companion object {
        private const val STALE_TIMEOUT_MS = 3_000L
        private const val MONITOR_INTERVAL_MS = 500L
    }

    init {
        observeVehicleData()
        monitorDataFreshness()
    }

    private fun observeVehicleData() {
        viewModelScope.launch {

            observeVehicleDataUseCase()
                .catch { throwable ->

                    if (throwable is CancellationException) {
                        throw throwable
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isConnected = false,
                        isStale = true,
                        errorMessage = "Unable to receive vehicle data."
                    )
                }
                .collect { incomingData ->

                    val result = validator.validate(incomingData)

                    lastEmissionTime = clock.nowMillis()

                    _uiState.value = HomeUiState(
                        vehicleData = result.data,
                        isLoading = false,
                        isConnected = true,
                        isStale = false,
                        errorMessage = null,
                        warningMessage = result.warning
                    )
                }
        }
    }

    private fun monitorDataFreshness() {
        viewModelScope.launch {

            while (true) {
                delay(MONITOR_INTERVAL_MS)

                val lastReceived = lastEmissionTime
                val now = clock.nowMillis()

                val stale = if (lastReceived == null) {
                    now >= STALE_TIMEOUT_MS
                } else {
                    now - lastReceived >= STALE_TIMEOUT_MS
                }

                if (stale) {
                    _uiState.value = _uiState.value.copy(
                        isConnected = false,
                        isStale = true,
                        errorMessage = "Vehicle data is unavailable or stale."
                    )
                }
            }
        }
    }
}
