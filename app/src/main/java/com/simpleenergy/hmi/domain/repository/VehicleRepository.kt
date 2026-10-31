package com.simpleenergy.hmi.domain.repository

import com.simpleenergy.hmi.domain.model.VehicleData
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun observeVehicleData(): Flow<VehicleData>
}