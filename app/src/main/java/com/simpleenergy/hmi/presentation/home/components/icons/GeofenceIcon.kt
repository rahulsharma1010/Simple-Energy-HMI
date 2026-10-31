package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simpleenergy.hmi.ui.theme.Orange
import com.simpleenergy.hmi.utils.d

@Composable
fun GeofenceIcon() {
    val stroke = 1.2.d
    Canvas(modifier = Modifier.size(17.d)) {
        drawCircle(Color(0xFF2A1608))
        drawCircle(
            color = Orange,
            radius = size.width * 0.45f,
            style = Stroke(stroke.toPx())
        )
        drawCircle(
            color = Orange,
            radius = size.width * 0.22f,
            style = Stroke(stroke.toPx())
        )
        drawCircle(color = Orange, radius = size.width * 0.07f)
    }
}