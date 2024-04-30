package com.salman.sagor.presentation.core

import androidx.compose.ui.graphics.Color
import com.salman.sagor.presentation.model.GraphValues
import kotlin.random.Random


val GraphValues.color: Color
    get() = Color(
        Random.nextInt(0, 255),
        Random.nextInt(0, 255),
        Random.nextInt(0, 255),
    )