package com.simpleenergy.hmi.core

import android.os.SystemClock
import javax.inject.Inject

interface ElapsedClock {
    fun nowMillis(): Long
}

class SystemElapsedClock @Inject constructor() : ElapsedClock {
    override fun nowMillis(): Long {
        return SystemClock.elapsedRealtime()
    }
}