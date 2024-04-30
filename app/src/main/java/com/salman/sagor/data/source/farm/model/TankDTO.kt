package com.salman.sagor.data.source.farm.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TankDTO(
    @SerialName("fish_type")
    val fishType: String,
    val packages: List<Package>,
    val status: String
)