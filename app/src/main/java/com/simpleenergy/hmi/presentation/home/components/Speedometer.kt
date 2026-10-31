
package com.simpleenergy.hmi.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Speedometer(
    speed: Float,
    modifier: Modifier = Modifier
) {
    val safeSpeed = speed.coerceIn(0f, 100f)

    val trackColor = Color(0xFF263342)
    val progressColor = Color(0xFF27D9B5)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val strokeWidth = 18.dp.toPx()

            val diameter = size.minDimension - strokeWidth

            val topLeft = Offset(
                x = (size.width - diameter) / 2,
                y = (size.height - diameter) / 2
            )

            drawArc(
                color = trackColor,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = topLeft,
                size = androidx.compose.ui.geometry.Size(
                    diameter,
                    diameter
                ),
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = progressColor,
                startAngle = 135f,
                sweepAngle = 270f * (safeSpeed / 100f),
                useCenter = false,
                topLeft = topLeft,
                size = androidx.compose.ui.geometry.Size(
                    diameter,
                    diameter
                ),
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = safeSpeed.toInt().toString(),
                color = Color.White,
                fontSize = 64.sp,
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = "km/h",
                color = Color(0xFF9AA8B7),
                fontSize = 18.sp
            )
        }
    }
}
