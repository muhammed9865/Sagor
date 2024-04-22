package com.salman.sagor.presentation.model

import androidx.compose.ui.graphics.Color

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/3/2024.
 */
data class GraphValues(
    val name: String,
    val history: List<Float>,
    val color: Color,
)