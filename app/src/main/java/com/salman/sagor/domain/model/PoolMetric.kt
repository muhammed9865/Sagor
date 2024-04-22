package com.salman.sagor.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/19/2024.
 */
data class PoolMetric(
    val name: String,
    val value: Float,
    val maxValue: Float,
    val boundaryValues: List<Int>,
    val valueType: MetricValueType,
)

enum class MetricValueType {
    Progress,
    Text,
}
