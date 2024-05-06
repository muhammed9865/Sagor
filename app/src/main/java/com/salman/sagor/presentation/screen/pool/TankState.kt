package com.salman.sagor.presentation.screen.pool

import com.salman.sagor.domain.model.GraphValues
import com.salman.sagor.domain.model.PoolMetric
import com.salman.sagor.presentation.core.UiState
import kotlinx.datetime.LocalDateTime

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/2/2024.
 */
data class TankState(
    val name: String = "",
    val currentReadings: List<PoolMetric> = emptyList(),
    val sensorsHistory: List<GraphValues> = emptyList(),
    val isLoading: Boolean = false,
    val failedToLoadDetails: Boolean = false,
    val lastUpdated: LocalDateTime = LocalDateTime(0, 1, 1, 0, 0)
) : UiState
