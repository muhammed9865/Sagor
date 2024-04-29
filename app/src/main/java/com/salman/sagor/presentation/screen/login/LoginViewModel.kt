package com.salman.sagor.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.salman.sagor.data.repository.UserRepository
import com.salman.sagor.presentation.core.MVIViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
): MVIViewModel<LoginAction, LoginState>(LoginState()) {
    override fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.PhoneNumberChanged -> { updatePhoneNumber(action.phoneNumber) }
            is LoginAction.LoginClicked -> { login() }
            is LoginAction.OtpChanged -> { updateOtp(action.otp) }
            is LoginAction.ResendOtpClicked -> { resendOtp() }
            is LoginAction.VerifyOTP -> { verifyOTP() }
            is LoginAction.NavigatedToVerification -> {
                updateState { copy(isOtpSent = false) }
            }
        }
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        val trimmed = phoneNumber.replace(" ", "")
        if (trimmed.length > 11) return
        val formatted = trimmed.chunked(3)
        updateState {
            copy(phoneNumber = formatted.joinToString(" "))
        }
    }

    private fun login() {
        updateState {
            copy(isLoggingIn = true)
        }
        val phoneNumber = state.value.phoneNumber.replace(" ", "")
        viewModelScope.launch {
            val isOtpSent = repository.login(phoneNumber).isSuccess
            updateState {
                copy(isLoggingIn = false, isOtpSent = isOtpSent)
            }
        }
    }

    private fun updateOtp(otp: String) {
        val trimmed = otp.replace(" ", "")
        if (trimmed.length > 11) return
        val formatted = trimmed.chunked(3).joinToString(" ")
        updateState {
            copy(otp = formatted)
        }
    }

    private fun resendOtp() {
        viewModelScope.launch {
            repository.login(state.value.phoneNumber)
        }
    }

    private fun verifyOTP() {
        val phoneNumber = state.value.phoneNumber.replace(" ", "")
        val otp = state.value.otp.replace(" ", "")
        updateState {
            copy(isVerifyingOtp = true)
        }
        viewModelScope.launch {
            repository.verifyOTP(phoneNumber, otp)
            updateState {
                copy(isVerifyingOtp = false)
            }
        }
    }
}
