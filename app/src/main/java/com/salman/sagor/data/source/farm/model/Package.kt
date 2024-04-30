package com.salman.sagor.data.source.farm.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Package(
    @SerialName("camera_sensor_readings")
    val cameraSensorReadings: List<CameraSensorReading>,
    @SerialName("last_checked_at")
    val lastCheckedAt: String,
    @SerialName("ph_sensor_readings")
    val phSensorReadings: List<PhSensorReading>,
    val status: String,
    @SerialName("temprature_sensor_readings")
    val tempratureSensorReadings: List<TempratureSensorReading>
)