package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d

@Composable
fun NavigationIcon() {
    Canvas(modifier = Modifier.size(15.d)) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w, h)
            lineTo(w * 0.5f, h * 0.72f)
            lineTo(0f, h)
            close()
        }
        drawPath(path, White)
    }
}