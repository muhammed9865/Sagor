package com.salman.sagor.presentation.core

import androidx.compose.ui.graphics.Color
import com.salman.sagor.domain.model.GraphValues
import com.salman.sagor.domain.model.PH_MAX_VALUE
import com.salman.sagor.domain.model.PH_SENSOR
import com.salman.sagor.domain.model.PoolMetric
import com.salman.sagor.domain.model.TEMPERATURE_MAX_VALUE
import com.salman.sagor.domain.model.TEMPERATURE_SENSOR


val GraphValues.color: Color
    get(): Color {
        return when (name) {
            "Temperature" -> Color.Red
            "pH" -> Color.Blue
            else -> Color.Black
        }
    }

val PoolMetric.boundaryValues: List<Int>
    get() = run {
        when (this.name) {
            PH_SENSOR -> listOf(0, 4, 6, PH_MAX_VALUE.toInt())
            TEMPERATURE_SENSOR -> listOf(0, 40, 60, TEMPERATURE_MAX_VALUE.toInt())
            else -> throw IllegalArgumentException("Unknown sensor name")
        }
    }