package com.simpleenergy.hmi.presentation.home.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d

@Composable
fun SettingsIcon() {
    Canvas(modifier = Modifier.size(15.d)) {
        val w = size.width
        val s = w * 0.42f
        val corner = CornerRadius(s * 0.2f)
        drawRoundRect(White, Offset(0f, 0f), Size(s, s), corner)
        drawRoundRect(White, Offset(w - s, 0f), Size(s, s), corner)
        drawRoundRect(White, Offset(0f, w - s), Size(s, s), corner)
        drawCircle(White, s / 2f, Offset(w - s / 2f, w - s / 2f))
    }
}