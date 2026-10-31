
package com.simpleenergy.hmi.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MotorStatusCard(
    isMotorOn: Boolean,
    modifier: Modifier = Modifier
) {
    val statusColor = if (isMotorOn) {
        Color(0xFF27D9B5)
    } else {
        Color(0xFFFF6464)
    }

    Column(
        modifier = modifier
            .background(
                Color(0xFF172332),
                RoundedCornerShape(18.dp)
            )
            .padding(20.dp)
    ) {

        Text(
            text = "MOTOR STATUS",
            color = Color(0xFF9AA8B7),
            fontSize = 13.sp
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = if (isMotorOn) "ON" else "OFF",
            color = statusColor,
            fontSize = 30.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = if (isMotorOn) {
                "Motor is running"
            } else {
                "Motor is switched off"
            },
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
    }
}
