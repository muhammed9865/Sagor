package com.salman.sagor.presentation.core

import androidx.compose.ui.graphics.Color
import com.salman.sagor.domain.model.GraphValues


val GraphValues.color: Color
    get(): Color {
        return when (name) {
            "Temperature" -> Color.Red
            "pH" -> Color.Blue
            else -> Color.Black
        }
    }