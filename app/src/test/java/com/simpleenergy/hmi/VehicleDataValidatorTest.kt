
package com.simpleenergy.hmi

import com.simpleenergy.hmi.core.VehicleDataValidator
import com.simpleenergy.hmi.domain.model.VehicleData
import com.simpleenergy.hmi.domain.model.VehicleStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class VehicleDataValidatorTest {

    private val validator = VehicleDataValidator()

    @Test
    fun `negative speed is converted to zero`() {

        val input = VehicleData(
            speed = -10f,
            isMotorOn = true,
            isKillSwitchActive = false,
            status = VehicleStatus.RIDING
        )

        val result = validator.validate(input)

        assertEquals(0f, result.data.speed)
        assertNotNull(result.warning)
    }

    @Test
    fun `valid speed is preserved`() {

        val input = VehicleData(
            speed = 25f,
            isMotorOn = true,
            isKillSwitchActive = false,
            status = VehicleStatus.RIDING
        )

        val result = validator.validate(input)

        assertEquals(25f, result.data.speed)
        assertNull(result.warning)
    }

    @Test
    fun `infinite speed is converted to zero`() {

        val input = VehicleData(
            speed = Float.POSITIVE_INFINITY,
            isMotorOn = true,
            isKillSwitchActive = false,
            status = VehicleStatus.RIDING
        )

        val result = validator.validate(input)

        assertEquals(0f, result.data.speed)
        assertNotNull(result.warning)
    }
}
