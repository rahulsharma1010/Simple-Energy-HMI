package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import com.simpleenergy.hmi.ui.theme.Yellow
import com.simpleenergy.hmi.utils.d

@Composable
fun SideStandIcon() {
    val stroke = 1.9.d
    Canvas(modifier = Modifier.size(15.d)) {
        val w = size.width
        val h = size.height
        drawLine(
            color = Yellow,
            start = Offset(w * 0.2f, h * 0.1f),
            end = Offset(w * 0.65f, h * 0.75f),
            strokeWidth = stroke.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Yellow,
            start = Offset(w * 0.5f, h * 0.75f),
            end = Offset(w, h * 0.75f),
            strokeWidth = stroke.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Yellow,
            start = Offset(0f, h * 0.95f),
            end = Offset(w, h * 0.95f),
            strokeWidth = stroke.toPx(),
            cap = StrokeCap.Round
        )
    }
}