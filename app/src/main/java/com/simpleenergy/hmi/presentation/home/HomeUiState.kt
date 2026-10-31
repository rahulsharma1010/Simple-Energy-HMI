package com.simpleenergy.hmi.presentation.home

import com.simpleenergy.hmi.domain.model.VehicleData

data class HomeUiState(
    val vehicleData: VehicleData? = null,
    val isLoading: Boolean = true,
    val isConnected: Boolean = false,
    val isStale: Boolean = false,
    val errorMessage: String? = null,
    val warningMessage: String? = null
)