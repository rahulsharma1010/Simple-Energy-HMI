package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.simpleenergy.hmi.ui.theme.Green
import com.simpleenergy.hmi.utils.d

@Composable
fun BatteryIcon() {
    val outline = 1.d
    val inset = 1.8.d
    Canvas(modifier = Modifier.size(17.d, 10.d)) {
        val w = size.width
        val h = size.height
        drawRoundRect(
            color = Green,
            topLeft = Offset(0f, 0f),
            size = Size(w * 0.88f, h),
            cornerRadius = CornerRadius(1.5.dp.toPx()),
            style = Stroke(outline.toPx())
        )
        drawRect(
            color = Green,
            topLeft = Offset(inset.toPx(), inset.toPx()),
            size = Size(w * 0.88f - inset.toPx() * 2f, h - inset.toPx() * 2f)
        )
        drawRect(
            color = Green,
            topLeft = Offset(w * 0.9f, h * 0.3f),
            size = Size(w * 0.1f, h * 0.4f)
        )
    }
}