package com.simpleenergy.hmi.data.provider

import com.simpleenergy.hmi.domain.model.VehicleData
import com.simpleenergy.hmi.domain.model.VehicleStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class SimulatedVehicleDataProvider @Inject constructor() :
    VehicleDataProvider {

    override fun observeVehicleData(): Flow<VehicleData> = flow {
        while (true) {
            // 1. PARKED
            emit(
                vehicle(
                    speed = 0f,
                    motorOn = false,
                    killSwitchActive = false,
                    status = VehicleStatus.PARKED
                )
            )
            delay(1000.milliseconds)

            // 2. READY
            emit(
                vehicle(
                    speed = 0f,
                    motorOn = true,
                    killSwitchActive = false,
                    status = VehicleStatus.READY
                )
            )
            delay(1000.milliseconds)

            // 3. RIDING: 1 to 25 km/h
            for (speed in 1..25) {
                emit(
                    vehicle(
                        speed = speed.toFloat(),
                        motorOn = true,
                        killSwitchActive = false,
                        status = VehicleStatus.RIDING
                    )
                )
                delay(20.milliseconds)
            }
            delay(1000.milliseconds)

            // 4. ACCELERATING: 26 to 50 km/h
            for (speed in 26..50) {
                emit(
                    vehicle(
                        speed = speed.toFloat(),
                        motorOn = true,
                        killSwitchActive = false,
                        status = VehicleStatus.ACCELERATING
                    )
                )
                delay(20.milliseconds)
            }
            delay(1000.milliseconds)

            // 5. DECELERATION: 49 to 1 km/h
            // Motor remains ON while the vehicle slows down.
            for (speed in 49 downTo 1) {
                emit(
                    vehicle(
                        speed = speed.toFloat(),
                        motorOn = true,
                        killSwitchActive = false,
                        status = VehicleStatus.RIDING
                    )
                )
                delay(20.milliseconds)
            }

            // 6. MOTOR DISABLED: vehicle has stopped.
            emit(
                vehicle(
                    speed = 0f,
                    motorOn = false,
                    killSwitchActive = true,
                    status = VehicleStatus.MOTOR_DISABLED
                )
            )
            delay(1000.milliseconds)
        }
    }

    private fun vehicle(
        speed: Float,
        motorOn: Boolean,
        killSwitchActive: Boolean,
        status: VehicleStatus
    ) = VehicleData(
        speed = speed,
        isMotorOn = motorOn,
        isKillSwitchActive = killSwitchActive,
        status = status
    )
}