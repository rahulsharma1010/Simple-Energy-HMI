package com.simpleenergy.hmi.data.repository

import com.simpleenergy.hmi.data.provider.VehicleDataProvider
import com.simpleenergy.hmi.domain.model.VehicleData
import com.simpleenergy.hmi.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val dataProvider: VehicleDataProvider
) : VehicleRepository {

    override fun observeVehicleData(): Flow<VehicleData> {
        return dataProvider.observeVehicleData()
    }
}