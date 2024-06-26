package com.salman.sagor.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
sealed class UserSendOTPStatus {
    data object OTPSent : UserSendOTPStatus()
    data object OTPNotSent : UserSendOTPStatus()
    data object PhoneNotValid : UserSendOTPStatus()
}