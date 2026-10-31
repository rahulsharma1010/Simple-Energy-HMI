package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.simpleenergy.hmi.ui.theme.Red
import com.simpleenergy.hmi.utils.d


@Composable
fun RapidBrakeIcon() {
    Canvas(modifier = Modifier.size(34.d, 22.d)) {
        val w = size.width
        val h = size.height

        fun bolt(dx: Float, color: Color) {
            val p = Path().apply {
                moveTo(w * (0.45f + dx), 0f)
                lineTo(w * (0.12f + dx), h * 0.58f)
                lineTo(w * (0.34f + dx), h * 0.58f)
                lineTo(w * (0.22f + dx), h)
                lineTo(w * (0.62f + dx), h * 0.36f)
                lineTo(w * (0.40f + dx), h * 0.36f)
                lineTo(w * (0.58f + dx), 0f)
                close()
            }
            drawPath(p, color)
        }

        bolt(0f, Red.copy(alpha = 0.55f))
        bolt(0.3f, Red)
    }
}