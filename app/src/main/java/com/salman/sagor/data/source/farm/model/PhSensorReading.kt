package com.salman.sagor.data.source.farm.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhSensorReading(
    @SerialName("read_every")
    val readEvery: Int,
    @SerialName("reading_status")
    val readingStatus: String,
    val value: String
)