package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d

@Composable
fun DndIcon() {
    val stroke = 1.7.d
    Canvas(modifier = Modifier.size(11.d)) {
        drawCircle(White)
        drawLine(
            color = Color.Black,
            start = Offset(size.width * 0.25f, size.height * 0.5f),
            end = Offset(size.width * 0.75f, size.height * 0.5f),
            strokeWidth = stroke.toPx(),
            cap = StrokeCap.Round
        )
    }
}