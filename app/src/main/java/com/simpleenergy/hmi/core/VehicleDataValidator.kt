package com.simpleenergy.hmi.core

import com.simpleenergy.hmi.domain.model.VehicleData
import javax.inject.Inject

data class ValidationResult(
    val data: VehicleData,
    val warning: String? = null
)

class VehicleDataValidator @Inject constructor() {

    fun validate(data: VehicleData): ValidationResult {

        return if (!data.speed.isFinite() || data.speed < 0f) {

            ValidationResult(
                data = data.copy(speed = 0f),
                warning = "Invalid speed received. Displaying 0 km/h."
            )

        } else {
            ValidationResult(data = data)
        }
    }
}