package com.salman.sagor.data.mapper

import com.salman.sagor.data.source.farm.model.Package
import com.salman.sagor.data.source.farm.model.TankDTO
import com.salman.sagor.domain.model.Tank
import com.salman.sagor.presentation.model.GraphValues
import kotlin.random.Random


fun TankDTO.toDomainTank(): Tank {
    val id = Random.nextInt()
    return Tank(
        id = id,
        name = "Pool $id",

    )
}

private fun Package.toGraphValues(): List<GraphValues> {
    val pHValues = phSensorReadings.map { it.value.toFloat() }
    val temperatureValues = tempratureSensorReadings.map { it.value.toFloat() }

    val pHGraphValues = GraphValues("pH", pHValues)
    val temperatureGraphValues = GraphValues("Temperature", temperatureValues)

    return listOf(pHGraphValues, temperatureGraphValues)
}
