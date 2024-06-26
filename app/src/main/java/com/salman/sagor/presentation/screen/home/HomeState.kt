package com.salman.sagor.presentation.screen.home

import com.salman.sagor.domain.model.Tank
import com.salman.sagor.presentation.core.UiState

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/2/2024.
 */
data class HomeState(
    val tanks: List<Tank> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = "",
) : UiState {
    val showEmptyTanksList: Boolean
        get() = tanks.isEmpty() && !isLoading
}
