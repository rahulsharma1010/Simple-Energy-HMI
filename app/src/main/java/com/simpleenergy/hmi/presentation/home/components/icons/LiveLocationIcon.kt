package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import com.simpleenergy.hmi.ui.theme.Blue
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d

@Composable
fun LiveLocationIcon() {
    val stroke = 1.2.d
    Canvas(modifier = Modifier.size(17.d)) {
        drawCircle(Blue)
        drawCircle(
            color = White,
            radius = size.width * 0.28f,
            style = Stroke(stroke.toPx())
        )
        drawCircle(color = White, radius = size.width * 0.09f)
    }
}