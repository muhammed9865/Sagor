package com.salman.sagor.domain.model

import com.salman.sagor.presentation.model.GraphValues

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/19/2024.
 */
data class Tank(
    val id: Int,
    val name: String,
    val history: List<GraphValues>,
    val metrics: List<PoolMetric>,
    val xValues: List<Int>,
    val yValues: List<Int>
)
