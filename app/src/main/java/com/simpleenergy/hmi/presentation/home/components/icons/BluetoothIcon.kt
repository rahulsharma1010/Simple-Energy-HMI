package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simpleenergy.hmi.ui.theme.Blue
import com.simpleenergy.hmi.utils.d

@Composable
fun BluetoothIcon() {
    val stroke = 1.3.d
    Canvas(modifier = Modifier.size(9.d, 12.d)) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.1f, h * 0.3f)
            lineTo(w * 0.9f, h * 0.72f)
            lineTo(w * 0.5f, h)
            lineTo(w * 0.5f, 0f)
            lineTo(w * 0.9f, h * 0.28f)
            lineTo(w * 0.1f, h * 0.7f)
        }
        drawPath(
            path = path,
            color = Blue,
            style = Stroke(
                width = stroke.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}