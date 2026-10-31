package com.simpleenergy.hmi.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Details
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simpleenergy.hmi.R
import com.simpleenergy.hmi.domain.model.VehicleData
import com.simpleenergy.hmi.presentation.home.components.icons.BluetoothIcon
import com.simpleenergy.hmi.presentation.home.components.CallPill
import com.simpleenergy.hmi.presentation.home.components.StatusChip
import com.simpleenergy.hmi.presentation.home.components.icons.BatteryIcon
import com.simpleenergy.hmi.presentation.home.components.icons.CruiseIcon
import com.simpleenergy.hmi.presentation.home.components.icons.DndIcon
import com.simpleenergy.hmi.presentation.home.components.icons.GeofenceIcon
import com.simpleenergy.hmi.presentation.home.components.icons.HighBeamIcon
import com.simpleenergy.hmi.presentation.home.components.icons.IncognitoIcon
import com.simpleenergy.hmi.presentation.home.components.icons.LiveLocationIcon
import com.simpleenergy.hmi.presentation.home.components.icons.MotorIcon
import com.simpleenergy.hmi.presentation.home.components.icons.NavigationIcon
import com.simpleenergy.hmi.presentation.home.components.icons.RapidBrakeIcon
import com.simpleenergy.hmi.presentation.home.components.icons.SettingsIcon
import com.simpleenergy.hmi.presentation.home.components.icons.SideStandIcon
import com.simpleenergy.hmi.ui.theme.Background
import com.simpleenergy.hmi.ui.theme.ChipGrey
import com.simpleenergy.hmi.ui.theme.Green
import com.simpleenergy.hmi.ui.theme.Grey
import com.simpleenergy.hmi.ui.theme.Orange
import com.simpleenergy.hmi.ui.theme.PanelLight
import com.simpleenergy.hmi.ui.theme.Red
import com.simpleenergy.hmi.ui.theme.Teal
import com.simpleenergy.hmi.ui.theme.White
import com.simpleenergy.hmi.ui.theme.Yellow
import com.simpleenergy.hmi.utils.LocalScale
import com.simpleenergy.hmi.utils.d
import com.simpleenergy.hmi.utils.s

private fun pillBackground(color: Color): Color =
    color.copy(alpha = 0.20f).compositeOver(Color(0xFF0A0C0F))

// ==================================================
// SCREEN
// Element numbers (1-36) in comments map to the reference screenshot.
// ==================================================

@Composable
fun HomeDashboardScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val vehicle = uiState.vehicleData

    val speed = vehicle?.speed
        ?.takeIf { it.isFinite() && it >= 0f }
        ?: 0f

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val scale = (maxHeight.value / 360f).coerceIn(0.7f, 1.6f)

        CompositionLocalProvider(LocalScale provides scale) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 20.d,
                        end = 14.d,
                        top = 4.d,
                        bottom = 6.d
                    )
            ) {

                // 2, 3, 4, 6, 7, 8, 9, 10, 12, 13
                StatusBar()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    // 5, 11, 18, 19, 20, 21, 22, 23, 24, 25
                    CenterDashboard(
                        speed = speed,
                        vehicle = vehicle,
                        modifier = Modifier.fillMaxSize()
                    )

                    // 1
                    ParkingSlider(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = (-20).d)
                            .padding(top = 53.d)
                    )

                    // 14, 15, 16, 17
                    RightFeaturePanel(
                        speed = speed,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .fillMaxWidth(0.21f)
                            .padding(top = 40.d)
                    )

                    // Additional assignment requirement (not one of the 36)
                    KillSwitchChip(
                        vehicle = vehicle,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }

                Spacer(Modifier.height(4.d))

                // 26 - 36
                BottomFeatureBar(vehicle = vehicle)

                val message = uiState.errorMessage
                    ?: uiState.warningMessage

                if (message != null) {
                    Text(
                        text = message,
                        color = Yellow,
                        fontSize = 9.s,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 3.d),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// ==================================================
// STATUS BAR  (2, 3, 4 | 6, 7, 8, 9, 10 | 12, 13)
// ==================================================

@Composable
private fun StatusBar(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.d)
    ) {

        // 2 Mobile paired, 3 Incognito, 4 DND
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BluetoothIcon()
            Spacer(Modifier.width(5.d))
            Text(
                text = "Pixel 9 Pro...  (18%)",
                color = Grey,
                fontSize = 10.s,
                maxLines = 1
            )
            Spacer(Modifier.width(26.d))
            IncognitoIcon()
            Spacer(Modifier.width(10.d))
            DndIcon()
        }

        // 6 Mobile notifications, 7 System notifications, 8 Active call,
        // 9 Live location, 10 Geofence
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusChip(icon = painterResource(R.drawable.baseline_message_24), count = "4")
            Spacer(Modifier.width(4.d))
            StatusChip(icon = painterResource(R.drawable.baseline_notifications_none_24), count = "2")
            Spacer(Modifier.width(6.d))
            CallPill()
            Spacer(Modifier.width(5.d))
            TriangleChip()
            Spacer(Modifier.width(8.d))
            LiveLocationIcon()
            Spacer(Modifier.width(8.d))
            GeofenceIcon()
        }

        // 12 Date & time, 13 Signal strength (+ data connection state)
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(Modifier.width(10.d))

            Text(
                text = "Fri 24 Oct  10:22 PM",
                color = Grey,
                fontSize = 10.s,
                maxLines = 1
            )

            Spacer(Modifier.width(10.d))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(3.d))
                    .background(White)
                    .padding(horizontal = 3.d, vertical = 1.d)
            ) {
                Text(
                    text = "LTE",
                    color = Color.Black,
                    fontSize = 7.s,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.width(4.d))

            SignalBars()
        }
    }
}

@Composable
private fun TriangleChip() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(ChipGrey)
            .padding(horizontal = 6.d, vertical = 2.d),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Details,
            contentDescription = "Details",
            tint = White,
            modifier = Modifier.size(14.d)
        )
    }
}

@Composable
private fun SignalBars() {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .padding(end = 1.d)
                    .width(2.d)
                    .height((3 + index * 2.5f).d)
                    .background(White)
            )
        }
    }
}

// ==================================================
// 1 - PARKING SLIDER
// ==================================================

@Composable
private fun ParkingSlider(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .width(31.d)
                .height(60.d)
                .clip(RoundedCornerShape(topEnd = 9.d, bottomEnd = 9.d))
                .background(PanelLight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PARKING »",
                color = White,
                fontSize = 8.s,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                softWrap = false,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .wrapContentSize(unbounded = true)
                    .rotate(90f)
            )
        }

        Spacer(Modifier.height(7.d))

        // Dotted LED strip under the tab
        Column(modifier = Modifier.padding(start = 6.d)) {
            repeat(9) { row ->
                Row {
                    repeat(5) {
                        Box(
                            modifier = Modifier
                                .padding(end = 2.d, bottom = 2.d)
                                .size(1.5.d)
                                .background(
                                    Grey.copy(alpha = 0.55f - row * 0.04f),
                                    CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

// ==================================================
// CENTRE  (5, 11, 18, 19, 20, 21, 22, 23, 24, 25)
// ==================================================

@Composable
private fun CenterDashboard(
    speed: Float,
    vehicle: VehicleData?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(8.d))

        // 5. Ride mode badge
        RideModeBadge()

        Spacer(Modifier.height(9.d))

        // 11 (SOC bar), 18 (ride mode pill), 19 (range)
        RideInfoCard(vehicle = vehicle)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            // 21, 23, 24, 25 (+ km/h label)
            SpeedVisualisation(
                speed = speed,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )

            // 20. Speedometer value
            Text(
                text = speed.toInt().toString(),
                color = White,
                fontSize = 88.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-2).sp,
                lineHeight = 88.s,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-12).d)
            )
        }



        // 22. Odometer
        OdometerSlider()
    }
}

// 5
@Composable
private fun RideModeBadge() {
    Box(
        modifier = Modifier.size(30.d),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val hex = Path().apply {
                moveTo(w * 0.5f, 0f)
                lineTo(w * 0.93f, h * 0.25f)
                lineTo(w * 0.93f, h * 0.75f)
                lineTo(w * 0.5f, h)
                lineTo(w * 0.07f, h * 0.75f)
                lineTo(w * 0.07f, h * 0.25f)
                close()
            }
            drawPath(
                path = hex,
                brush = Brush.verticalGradient(
                    listOf(Color(0xFFFF8A1F), Color(0xFFFF3B1F))
                )
            )
        }
        Text(
            text = "S",
            color = White,
            fontSize = 16.s,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic
        )
    }
}

// 11 + 18 + 19
@Composable
private fun RideInfoCard(
    vehicle: VehicleData?
) {
    val barHeight = 5.d

    Box(
        modifier = Modifier
            .fillMaxWidth(0.32f)
            .height(52.d)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val body = Path().apply {
                moveTo(0f, 0f)
                lineTo(w, 0f)
                lineTo(w * 0.955f, h * 0.80f)
                quadraticTo(w * 0.94f, h, w * 0.88f, h)
                lineTo(w * 0.12f, h)
                quadraticTo(w * 0.06f, h, w * 0.045f, h * 0.80f)
                close()
            }

            drawPath(
                path = body,
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF353A41), Color(0xFF14161A))
                )
            )

            // 11. Alternate battery SOC visual bar (100%)
            drawRoundRect(
                color = Green,
                topLeft = Offset.Zero,
                size = Size(w, barHeight.toPx()),
                cornerRadius = CornerRadius(barHeight.toPx() / 2f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.d, end = 14.d, top = 7.d),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // 18. Ride mode pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF1A1C21))
                    .border(
                        1.d,
                        Grey.copy(alpha = 0.35f),
                        RoundedCornerShape(50)
                    )
                    .padding(horizontal = 8.d, vertical = 3.d),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = vehicle?.status?.name
                        ?.replace("_", " ") ?: "WAITING",
                    color = Orange,
                    fontSize = 12.s,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1
                )
                Spacer(Modifier.width(5.d))
                Text(text = "➤", color = Grey, fontSize = 8.s)
            }

            // 19. Range
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "62",
                    color = White,
                    fontSize = 26.s,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(3.d))
                Column {
                    Text(text = "km", color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Range", color = Grey, fontSize = 10.sp)
                }
            }
        }
    }
}

// 21, 23, 24, 25
@Composable
private fun SpeedVisualisation(
    speed: Float,
    modifier: Modifier
) {
    val progress = (speed / 100f).coerceIn(0f, 1f)
    val thrusterActive = speed > 80f
    val dotRadius = 1.2.d
    val glow = 6.d
    val core = 2.5.d
    val bandThickness = 6.d

    Box(
        modifier = modifier
            .fillMaxWidth(0.86f)
            .height(77.d)
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Cockpit body
            val body = Path().apply {
                moveTo(0f, h * 0.66f)
                lineTo(w * 0.28f, h * 0.04f)
                quadraticTo(w * 0.5f, 0f, w * 0.72f, h * 0.04f)
                lineTo(w, h * 0.66f)
                lineTo(w * 0.90f, h * 0.97f)
                lineTo(w * 0.10f, h * 0.97f)
                close()
            }
            drawPath(
                path = body,
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF2B2E34), Color(0xFF090A0C))
                )
            )

            // 24. Thruster visualisation (lit above 80 km/h)
            val dotColor = if (thrusterActive) {
                Color(0xFFFF9C8C)
            } else {
                Color(0xFF30333A)
            }
            val r = dotRadius.toPx()
            for (row in 0..3) {
                val fy = 0.28f + row * 0.07f
                val edge = ((0.66f - fy) / 2.214f).coerceAtLeast(0f) + 0.03f
                val maxX = if (fy < 0.5f) 0.23f else 0.105f
                var fx = edge
                while (fx <= maxX) {
                    drawCircle(dotColor, r, Offset(w * fx, h * fy))
                    drawCircle(dotColor, r, Offset(w * (1f - fx), h * fy))
                    fx += 0.0135f
                }
            }

            // 21. Blinker / warner (harsh acceleration, rapid brake)
            listOf(
                Offset(w * 0.21f, h * 0.22f) to Offset(w * 0.285f, h * 0.08f),
                Offset(w * 0.79f, h * 0.22f) to Offset(w * 0.715f, h * 0.08f)
            ).forEach { (start, end) ->
                drawLine(
                    color = Orange.copy(alpha = 0.35f),
                    start = start,
                    end = end,
                    strokeWidth = glow.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Red,
                    start = start,
                    end = end,
                    strokeWidth = core.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // 23. Speedometer visualisation: red band fills from the centre
            val band = Path().apply {
                moveTo(w * 0.06f, h * 0.52f)
                lineTo(w * 0.94f, h * 0.52f)
                lineTo(w * 0.84f, h * 0.92f)
                lineTo(w * 0.16f, h * 0.92f)
                close()
            }

            drawPath(band, Red.copy(alpha = 0.25f))

            if (progress > 0f) {
                clipRect(
                    left = w * (0.5f - progress * 0.5f),
                    right = w * (0.5f + progress * 0.5f)
                ) {
                    drawPath(
                        path = band,
                        brush = Brush.verticalGradient(
                            listOf(Color(0xFFFF4B3E), Color(0xFFD9120A))
                        )
                    )
                    drawLine(
                        color = Color(0xFFFF8F85),
                        start = Offset(w * 0.12f, h * 0.52f),
                        end = Offset(w * 0.88f, h * 0.52f),
                        strokeWidth = bandThickness.toPx() / 6f
                    )
                }
            }

        }

        Text(
            text = "km/h",
            color = Grey,
            fontSize = 9.s,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )
    }
}

// 22
@Composable
private fun OdometerSlider() {
    val readings = listOf(
        "ODO" to "1324 km",
    )
    var index by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.34f)
            .height(22.d),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "‹",
            color = Grey.copy(alpha = 0.7f),
            fontSize = 16.s,
            modifier = Modifier
                .clickable {
                    index = (index + readings.size - 1) % readings.size
                }
                .padding(horizontal = 6.d)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = readings[index].first,
                color = Grey,
                fontSize = 10.s,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.width(8.d))
            Text(
                text = readings[index].second,
                color = White,
                fontSize = 11.s,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = "›",
            color = Grey.copy(alpha = 0.7f),
            fontSize = 16.s,
            modifier = Modifier
                .clickable {
                    index = (index + 1) % readings.size
                }
                .padding(horizontal = 6.d)
        )
    }
}

// ==================================================
// RIGHT PANEL  (14, 15, 16, 17)
// ==================================================

@Composable
private fun RightFeaturePanel(
    speed: Float,
    modifier: Modifier = Modifier
) {
    val overspeed = speed > 80f

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {

        // 14. Rapid brake alert
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.d),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RapidBrakeIcon()
                Spacer(Modifier.width(5.d))
                Column {
                    Text(text = "Rapid", color = White, fontSize = 9.s)
                    Text(
                        text = "Brake",
                        color = White,
                        fontSize = 13.s,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 15. SuperHold
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.d),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                HoldArcs(left = true)
                Spacer(Modifier.width(2.d))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Teal)
                        .padding(horizontal = 11.d, vertical = 3.d),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "SUPER",
                            color = Color.Black,
                            fontSize = 6.s,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 7.s
                        )
                        Text(
                            text = "Hold",
                            color = Color.Black,
                            fontSize = 12.s,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 12.s
                        )
                    }
                }
                Spacer(Modifier.width(2.d))
                HoldArcs(left = false)
            }
        }

        // 16. Overspeed / speed limit
        val speedColor = if (overspeed) Red else Yellow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.d),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(31.d)
                        .border(2.d, speedColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "80",
                            color = White,
                            fontSize = 11.s,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 11.s
                        )
                        Text(
                            text = "km/h",
                            color = Grey,
                            fontSize = 5.s,
                            lineHeight = 5.s
                        )
                    }
                }
                Spacer(Modifier.width(6.d))
                Column {
                    Text(
                        text = "Speed",
                        color = White,
                        fontSize = 9.s,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (overspeed) "Exceeded" else "Limit",
                        color = if (overspeed) White else Grey,
                        fontSize = 9.s,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 17. Cruise control
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.d),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF080B0C))
                    .border(
                        1.d,
                        Green.copy(alpha = 0.35f),
                        RoundedCornerShape(50)
                    )
                    .padding(horizontal = 9.d, vertical = 3.d),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CruiseIcon()
                Spacer(Modifier.width(6.d))
                Column {
                    Text(
                        text = "Active",
                        color = Green,
                        fontSize = 8.s,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 9.s
                    )
                    Text(
                        text = "72 km/h",
                        color = White,
                        fontSize = 10.s,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 11.s
                    )
                }
            }
        }
    }
}


@Composable
private fun HoldArcs(left: Boolean) {
    val stroke = 1.6.d
    Canvas(modifier = Modifier.size(15.d, 24.d)) {
        val w = size.width
        val h = size.height
        val cy = h / 2f
        val theta = 40f
        val cx = if (left) w * 1.2f else -w * 0.2f

        repeat(3) { i ->
            val r = w * (0.4f + 0.4f * i)
            drawArc(
                color = Teal.copy(alpha = 1f - i * 0.28f),
                startAngle = if (left) 180f - theta else -theta,
                sweepAngle = theta * 2f,
                useCenter = false,
                topLeft = Offset(cx - r, cy - r),
                size = Size(r * 2f, r * 2f),
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

// ==================================================
// KILL SWITCH (assignment requirement, separate from the 36 elements)
// ==================================================

@Composable
private fun KillSwitchChip(
    vehicle: VehicleData?,
    modifier: Modifier = Modifier
) {
    val killColor = when (vehicle?.isKillSwitchActive) {
        true -> Red
        false -> Green
        null -> Grey
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.d))
            .background(ChipGrey)
            .padding(horizontal = 6.d, vertical = 3.d),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.d)
                .background(killColor, CircleShape)
        )
        Spacer(Modifier.width(4.d))
        Column {
            Text(
                text = "KILL SWITCH",
                color = Grey,
                fontSize = 6.s,
                lineHeight = 7.s
            )
            Text(
                text = when (vehicle?.isKillSwitchActive) {
                    true -> "ACTIVE"
                    false -> "INACTIVE"
                    null -> "N/A"
                },
                color = killColor,
                fontSize = 8.s,
                fontWeight = FontWeight.Bold,
                lineHeight = 9.s
            )
        }
    }
}

// ==================================================
// BOTTOM BAR  (26 - 36)
// Pill widths use the same proportions as the reference screenshot.
// ==================================================

@Composable
private fun BottomFeatureBar(
    vehicle: VehicleData?
) {
    val motorColor = when (vehicle?.isMotorOn) {
        true -> Green
        false -> Red
        null -> Grey
    }
    val motorText = when (vehicle?.isMotorOn) {
        true -> "ON"
        false -> "OFF"
        null -> "--"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(33.d),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 26. Settings menu
        BottomPill(58f, PanelLight) { SettingsIcon() }
        Gap(7f)

        // 27. Maps navigation
        BottomPill(41f, PanelLight) { NavigationIcon() }
        Gap(62f)

        // 28. High beam
        BottomPill(35f, pillBackground(PanelLight)) { HighBeamIcon() }
        Gap(2f)

        // 29. Side stand
        BottomPill(36f, pillBackground(PanelLight)) { SideStandIcon() }
        Gap(4f)

        // 30. Left indicator
        BottomPill(36f, pillBackground(PanelLight)) {
            Icon(
                imageVector = Icons.Default.ArrowCircleLeft,
                contentDescription = "Arrow Down",
                tint = Color(0xFF00b300)
            )
        }
        Gap(4f)

        // 31. Service due
        BottomPill(38f, pillBackground(PanelLight)) {
            Icon(
                painter = painterResource(id = R.drawable.repair),
                contentDescription = "Arrow Down",
                modifier = Modifier.size(24.dp),
                tint = Orange
            )
//            ServiceIcon()
        }
        Gap(40f)

        // 32. Traction control
        BottomPill(38f, pillBackground(PanelLight)) {
            Box(
                modifier = Modifier
                    .size(19.d)
                    .border(1.d, Green, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TC",
                    color = Green,
                    fontSize = 7.s,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Gap(2f)

        // 33. Right indicator
        BottomPill(36f, pillBackground(PanelLight)) {
            Icon(
                imageVector = Icons.Default.ArrowCircleRight,
                contentDescription = "Arrow Down",
                tint = Color(0xFF00b300)
            )

        }
        Gap(9f)

        // 34. TPMS
        BottomPill(40f, PanelLight) {
            Text(
                text = "( ! )",
                color = Grey,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Gap(5f)

        // 35. Battery pill with SOC
        BottomPill(113f, pillBackground(PanelLight)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BatteryIcon()
                Spacer(Modifier.width(6.d))
                Text(
                    text = "100%",
                    color = Green,
                    fontSize = 15.s,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Gap(9f)

        // 36. Motor status
        BottomPill(60f, pillBackground(motorColor)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MotorIcon(color = motorColor)
                Spacer(Modifier.width(5.d))
                Text(
                    text = motorText,
                    color = motorColor,
                    fontSize = 12.s,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomPill(
    weight: Float,
    background: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight()
            .clip(RoundedCornerShape(50))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


@Composable
private fun RowScope.Gap(weight: Float) {
    Spacer(Modifier.weight(weight))
}
