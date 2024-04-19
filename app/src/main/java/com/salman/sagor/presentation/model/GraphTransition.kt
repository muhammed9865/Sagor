package com.salman.sagor.presentation.model

import androidx.compose.runtime.State

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/3/2024.
 */
data class GraphTransition(
    val slices: List<State<Float>>
)