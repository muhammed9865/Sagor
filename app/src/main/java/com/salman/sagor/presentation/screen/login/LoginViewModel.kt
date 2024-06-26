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
) : MVIViewModel<LoginAction, LoginState>(LoginState()) {

    init {
        println("LoginViewModel created")
    }
    override fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.PhoneNumberChanged -> {
                updatePhoneNumber(action.phoneNumber)
            }

            is LoginAction.LoginClicked -> {
                login()
            }

            is LoginAction.OtpChanged -> {
                updateOtp(action.otp)
            }

            is LoginAction.ResendOtpClicked -> {
                resendOtp()
            }

            is LoginAction.VerifyOTP -> {
                verifyOTP()
            }

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
                copy(
                    isLoggingIn = false,
                    isOtpSent = isOtpSent,
                    message = if (isOtpSent) "" else "Something went wrong, please try again."
                )
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
            val phoneNumber = state.value.phoneNumber.replace(" ", "")
            repository.login(phoneNumber).onSuccess {
                updateTemporarilyState(resetState = state.value.copy(message = "")) {
                    copy(message = "A new OTP has been sent to your phone number.")
                }
            }
        }
    }

    private fun verifyOTP() {
        val phoneNumber = state.value.phoneNumber.replace(" ", "")
        val otp = state.value.otp.replace(" ", "")
        updateState {
            copy(isVerifyingOtp = true)
        }
        viewModelScope.launch {
            val isVerified = repository.verifyOTP(phoneNumber, otp).isSuccess
            if (isVerified) {
                updateState {
                    copy(
                        isVerifyingOtp = false,
                        navigateToHome = true,
                    )
                }
            } else {
                updateState {
                    copy(isVerifyingOtp = false)
                }
                updateTemporarilyState(
                    resetState = state.value.copy(message = "")
                ) {
                    copy(message = "Invalid OTP, please try again.")
                }
            }
        }
    }
}
