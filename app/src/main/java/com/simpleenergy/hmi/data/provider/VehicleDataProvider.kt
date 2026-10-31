package com.simpleenergy.hmi.data.provider

import com.simpleenergy.hmi.domain.model.VehicleData
import kotlinx.coroutines.flow.Flow

interface VehicleDataProvider {
    fun observeVehicleData(): Flow<VehicleData>
}