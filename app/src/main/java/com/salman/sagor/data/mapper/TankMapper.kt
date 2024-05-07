package com.salman.sagor.data.mapper

import com.salman.sagor.data.source.farm.model.PackageDTO
import com.salman.sagor.data.source.farm.model.TankDTO
import com.salman.sagor.domain.model.COUNTER_START_ANGLE
import com.salman.sagor.domain.model.GraphValues
import com.salman.sagor.domain.model.MetricValueType
import com.salman.sagor.domain.model.Package
import com.salman.sagor.domain.model.PoolMetric
import com.salman.sagor.domain.model.Tank
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

private const val PH_MAX_VALUE = 10f
private const val TEMPERATURE_MAX_VALUE = 100f

fun TankDTO.toDomainTank(): Tank {
    return Tank(
        id = id,
        name = "Pool $id",
        packages = packages.map { it.toDomainPackage() },
    )
}

private fun PackageDTO.toDomainPackage(): Package {
    val sensorsHistory = getSensorsHistory()
    val currentSensorsReadings = getCurrentTankMetrics()
    val lastUpdatedDate = getLastUpdatedDate()
    return Package(sensorsHistory, currentSensorsReadings, lastUpdatedDate)
}

private fun PackageDTO.getSensorsHistory(): List<GraphValues> {
    val pHValuesHistory = phSensorReadings.map { it.value.toFloat() }.toMutableList()
    if (pHValuesHistory.size < 8) { // TODO Remove in production
        pHValuesHistory.addAll(List(20 - pHValuesHistory.size) { Random.nextFloat() })
    }
    val temperatureValuesHistory =
        tempratureSensorReadings.map { it.value.toFloat() }.toMutableList()
    if (temperatureValuesHistory.size < 8) { // TODO Remove in production
        temperatureValuesHistory.addAll(List(20 - temperatureValuesHistory.size) { Random.nextFloat() })
    }

    val pHGraphValues = GraphValues("pH", pHValuesHistory)
    val temperatureGraphValues = GraphValues("Temperature", temperatureValuesHistory)
    return listOf(pHGraphValues, temperatureGraphValues)
}

private fun PackageDTO.getCurrentTankMetrics(): List<PoolMetric> {
    val currentPHReadings = phSensorReadings.last().value.toFloat()
    val currentTemperatureReadings = tempratureSensorReadings.last().value.toFloat()
    val pHPoolMetric = PoolMetric(
        name = "pH",
        value = currentPHReadings,
        maxValue = PH_MAX_VALUE,
        boundaryValues = createBoundaryValues(PH_MAX_VALUE.toInt()),
        valueType = MetricValueType.Progress,
    )
    val temperaturePoolMetric = PoolMetric(
        name = "Temperature",
        value = currentTemperatureReadings,
        maxValue = TEMPERATURE_MAX_VALUE,
        boundaryValues = createBoundaryValues(TEMPERATURE_MAX_VALUE.toInt()),
        valueType = MetricValueType.Progress
    )

    return listOf(pHPoolMetric, temperaturePoolMetric)
}

private fun PackageDTO.getLastUpdatedDate(): LocalDateTime {
    return Instant.parse(lastCheckedAt).toLocalDateTime(TimeZone.currentSystemDefault())
}

private fun createBoundaryValues(to: Int): List<Int> {
    val from = 0
    val count = 4

    fun getValueForIndex(index: Int): Int = run {
        when (index) {
            0 -> from
            in 1 until count - 1 -> (COUNTER_START_ANGLE.toInt() * (index) * to) / 360 // value = (START_ANGLE (135) * index  * to) / 360 (circle)
            else -> to
        }

    }
    return List(count, ::getValueForIndex)
}