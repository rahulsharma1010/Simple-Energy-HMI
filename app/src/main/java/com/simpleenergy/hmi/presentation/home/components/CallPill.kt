package com.simpleenergy.hmi.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.simpleenergy.hmi.ui.theme.Red
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d
import com.simpleenergy.hmi.utils.s

@Composable
fun CallPill() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF3A0A0C))
            .border(1.d, Red.copy(alpha = 0.6f), RoundedCornerShape(50))
            .padding(horizontal = 8.d, vertical = 2.d),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "01:48",
            color = White,
            fontSize = 9.s,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.width(5.d))
        Text(text = "☎", color = Red, fontSize = 10.s)
    }
}