package com.salman.sagor.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/30/2024.
 */
data class Package(
    val sensorsHistory: List<GraphValues>,
    val currentReadings: List<PoolMetric>,
    val lastUpdated: LocalDateTime
)
