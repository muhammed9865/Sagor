package com.salman.sagor.presentation.screen.login

import com.salman.sagor.presentation.core.UiState

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
data class LoginState(
    val phoneNumber: String = "",
    val isLoggingIn: Boolean = false,
    val isVerifyingOtp: Boolean = false,
    val isOtpSent: Boolean = false,
    val otp: String = "",
    val navigateToHome: Boolean = false,
    val message: String = "",
): UiState {
    val isPhoneNumberValid: Boolean
        get() = phoneNumber.replace(" ", "").length == 11

    val isOtpValid: Boolean
        get() = otp.replace(" ", "").length == 6
}
