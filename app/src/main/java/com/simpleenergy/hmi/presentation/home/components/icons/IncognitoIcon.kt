package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d

@Composable
fun IncognitoIcon() {
    val stroke = 1.d
    Canvas(modifier = Modifier.size(13.d, 11.d)) {
        val w = size.width
        val h = size.height
        val hat = Path().apply {
            moveTo(w * 0.2f, h * 0.45f)
            lineTo(w * 0.33f, 0f)
            lineTo(w * 0.67f, 0f)
            lineTo(w * 0.8f, h * 0.45f)
            close()
        }
        drawPath(hat, White)
        drawLine(
            color = White,
            start = Offset(0f, h * 0.5f),
            end = Offset(w, h * 0.5f),
            strokeWidth = stroke.toPx() * 1.2f,
            cap = StrokeCap.Round
        )
        drawCircle(
            color = White,
            radius = w * 0.16f,
            center = Offset(w * 0.3f, h * 0.8f),
            style = Stroke(stroke.toPx())
        )
        drawCircle(
            color = White,
            radius = w * 0.16f,
            center = Offset(w * 0.7f, h * 0.8f),
            style = Stroke(stroke.toPx())
        )
    }
}