package com.simpleenergy.hmi.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.simpleenergy.hmi.ui.theme.ChipGrey
import com.simpleenergy.hmi.ui.theme.Grey
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.utils.d
import com.simpleenergy.hmi.utils.s

@Composable
fun StatusChip(
    icon: Painter,
    count: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.d))
            .background(ChipGrey)
            .padding(horizontal = 6.d, vertical = 2.d),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = "Arrow Down",
            tint = Grey,
            modifier = Modifier.size(14.dp)
        )
        Spacer(Modifier.width(3.d))
        Text(
            text = count,
            color = White,
            fontSize = 9.s,
            fontWeight = FontWeight.Bold
        )
    }
}