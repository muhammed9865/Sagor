package com.salman.sagor.presentation.screen.login

import com.salman.sagor.presentation.core.UiAction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
sealed class LoginAction: UiAction {
    data class PhoneNumberChanged(val phoneNumber: String): LoginAction()
    data object LoginClicked: LoginAction()
    data class OtpChanged(val otp: String): LoginAction()
    data object ResendOtpClicked: LoginAction()
    data object VerifyOTP : LoginAction()
    data object NavigatedToVerification: LoginAction()
}
