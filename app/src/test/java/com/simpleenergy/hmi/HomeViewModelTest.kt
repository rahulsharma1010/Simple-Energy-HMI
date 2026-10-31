
package com.simpleenergy.hmi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import com.simpleenergy.hmi.core.ElapsedClock
import com.simpleenergy.hmi.core.VehicleDataValidator
import com.simpleenergy.hmi.domain.model.VehicleData
import com.simpleenergy.hmi.domain.model.VehicleStatus
import com.simpleenergy.hmi.domain.repository.VehicleRepository
import com.simpleenergy.hmi.domain.usecase.ObserveVehicleDataUseCase
import com.simpleenergy.hmi.presentation.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcher: TestDispatcher
    private lateinit var viewModelStore: ViewModelStore

    @Before
    fun setup() {
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        viewModelStore = ViewModelStore()
    }

    @After
    fun tearDown() {
        viewModelStore.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun `vehicle data updates UI state`() = runTest(dispatcher) {

        val vehicleData = VehicleData(
            speed = 25f,
            isMotorOn = true,
            isKillSwitchActive = false,
            status = VehicleStatus.RIDING
        )

        val repository = FakeVehicleRepository(vehicleData)

        val clock = object : ElapsedClock {
            override fun nowMillis(): Long = 1_000L
        }

        val viewModel = HomeViewModel(
            observeVehicleDataUseCase =
                ObserveVehicleDataUseCase(repository),
            validator = VehicleDataValidator(),
            clock = clock
        )

        runCurrent()

        val state = viewModel.uiState.value

        assertEquals(25f, state.vehicleData?.speed)
        assertEquals(true, state.vehicleData?.isMotorOn)
        assertTrue(state.isConnected)
        assertEquals(false, state.isStale)
    }

    @Test
    fun `missing emissions mark data as stale`() = runTest(dispatcher) {

        val repository = FakeVehicleRepository(null)

        val clock = object : ElapsedClock {
            override fun nowMillis(): Long = testScheduler.currentTime
        }

        val viewModel = HomeViewModel(
            observeVehicleDataUseCase =
                ObserveVehicleDataUseCase(repository),
            validator = VehicleDataValidator(),
            clock = clock
        )

        runCurrent()
        advanceTimeBy(3_500L)
        runCurrent()

        assertTrue(viewModel.uiState.value.isStale)
        assertEquals(
            "Vehicle data is unavailable or stale.",
            viewModel.uiState.value.errorMessage
        )
    }

    private class FakeVehicleRepository(
        private val data: VehicleData?
    ) : VehicleRepository {

        override fun observeVehicleData() =
            if (data == null) {
                kotlinx.coroutines.flow.emptyFlow()
            } else {
                flowOf(data)
            }
    }
}
