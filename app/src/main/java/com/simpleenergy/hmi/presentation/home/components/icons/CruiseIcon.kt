package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simpleenergy.hmi.ui.theme.Green
import com.simpleenergy.hmi.utils.d

@Composable
fun CruiseIcon() {
    val stroke = 1.6.d
    Canvas(modifier = Modifier.size(17.d)) {
        drawArc(
            color = Green,
            startAngle = 150f,
            sweepAngle = 240f,
            useCenter = false,
            style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
        )
        drawLine(
            color = Green,
            start = center,
            end = Offset(size.width * 0.78f, size.height * 0.3f),
            strokeWidth = stroke.toPx(),
            cap = StrokeCap.Round
        )
    }
}