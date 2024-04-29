package com.salman.sagor.data.source.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
@Serializable
data class SendOTPDTO(
    @SerialName("phone_number")
    val phoneNumber: String
)
