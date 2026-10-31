package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import com.simpleenergy.hmi.ui.theme.Blue
import com.simpleenergy.hmi.utils.d

@Composable
fun HighBeamIcon() {
    val stroke = 1.6.d
    Canvas(modifier = Modifier.size(17.d, 13.d)) {
        val w = size.width
        val h = size.height

        listOf(0.2f, 0.5f, 0.8f).forEach { fraction ->
            drawLine(
                color = Blue,
                start = Offset(w * 0.1f, h * fraction),
                end = Offset(w * 0.6f, h * fraction),
                strokeWidth = stroke.toPx(),
                cap = StrokeCap.Round
            )
        }

        drawArc(
            color = Blue,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(0f, 0f),
            size = Size(w * 1f, h)
        )

    }
}
