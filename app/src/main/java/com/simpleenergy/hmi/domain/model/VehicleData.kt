package com.simpleenergy.hmi.domain.model

data class VehicleData(
    val speed: Float,
    val isMotorOn: Boolean,
    val isKillSwitchActive: Boolean,
    val status: VehicleStatus,
    val timestamp: Long = System.currentTimeMillis()
)