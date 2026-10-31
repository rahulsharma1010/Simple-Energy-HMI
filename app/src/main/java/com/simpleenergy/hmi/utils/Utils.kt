package com.simpleenergy.hmi.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalScale = compositionLocalOf { 1f }

val Number.d: Dp
    @Composable get() = (this.toFloat() * LocalScale.current).dp

val Number.s: TextUnit
    @Composable get() = (this.toFloat() * LocalScale.current).sp