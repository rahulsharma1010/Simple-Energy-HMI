package com.simpleenergy.hmi.domain.usecase

import com.simpleenergy.hmi.domain.model.VehicleData
import com.simpleenergy.hmi.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveVehicleDataUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    operator fun invoke(): Flow<VehicleData> {
        return repository.observeVehicleData()
    }
}