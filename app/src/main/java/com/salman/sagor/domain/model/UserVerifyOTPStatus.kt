package com.salman.sagor.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
sealed class UserVerifyOTPStatus {
    data object OTPNotValid : UserVerifyOTPStatus()
    data object UserNotFound : UserVerifyOTPStatus()
    data class OTPVerified(val accessToken: String, val refreshToken: String) :
        UserVerifyOTPStatus()
}