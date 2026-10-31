package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simpleenergy.hmi.utils.d

@Composable
fun MotorIcon(color: Color) {
    val stroke = 1.3.d
    Canvas(modifier = Modifier.size(13.d)) {
        drawCircle(
            color = color,
            radius = size.width * 0.45f,
            style = Stroke(stroke.toPx())
        )
        drawCircle(color = color, radius = size.width * 0.15f)
    }
}