package com.salman.sagor.data.mapper

import com.salman.sagor.data.source.farm.model.PackageDTO
import com.salman.sagor.data.source.farm.model.TankDTO
import com.salman.sagor.domain.model.GraphValues
import com.salman.sagor.domain.model.IS_TESTING
import com.salman.sagor.domain.model.MetricValueType
import com.salman.sagor.domain.model.PH_MAX_VALUE
import com.salman.sagor.domain.model.PH_SENSOR
import com.salman.sagor.domain.model.Package
import com.salman.sagor.domain.model.PoolMetric
import com.salman.sagor.domain.model.TEMPERATURE_MAX_VALUE
import com.salman.sagor.domain.model.TEMPERATURE_SENSOR
import com.salman.sagor.domain.model.Tank
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random


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
    val currentPHReadings = if (!IS_TESTING)
        phSensorReadings.last().value.toFloat()
    else
        getTestingPHValue()

    val currentTemperatureReadings = if (!IS_TESTING)
        tempratureSensorReadings.last().value.toFloat()
    else
        getTestingTemperatureValue()

    val pHPoolMetric = PoolMetric(
        name = PH_SENSOR,
        value = currentPHReadings,
        maxValue = PH_MAX_VALUE,
        valueType = MetricValueType.Progress,
    )
    val temperaturePoolMetric = PoolMetric(
        name = TEMPERATURE_SENSOR,
        value = currentTemperatureReadings,
        maxValue = TEMPERATURE_MAX_VALUE,
        valueType = MetricValueType.Progress
    )

    if (IS_TESTING)
        testingCount = (testingCount + 1) % 10
    return listOf(pHPoolMetric, temperaturePoolMetric)
}

private fun PackageDTO.getLastUpdatedDate(): LocalDateTime {
    return Instant.parse(lastCheckedAt).toLocalDateTime(TimeZone.currentSystemDefault())
}

private var testingCount = 0
private fun getTestingPHValue(): Float {
    val stepSize = PH_MAX_VALUE / 10
    return stepSize * testingCount
}

private fun getTestingTemperatureValue(): Float {
    val stepSize = TEMPERATURE_MAX_VALUE / 10
    return stepSize * testingCount
}