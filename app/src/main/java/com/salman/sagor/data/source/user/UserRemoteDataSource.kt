package com.salman.sagor.data.source.user

import android.util.Log
import com.salman.sagor.data.source.user.model.SendOTPDTO
import com.salman.sagor.data.source.user.model.VerifyOTPDTO
import com.salman.sagor.data.source.user.model.VerifyOTPResponse
import com.salman.sagor.domain.model.UserSendOTPStatus
import com.salman.sagor.domain.model.UserVerifyOTPStatus
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
class UserRemoteDataSource @Inject constructor(
    private val client: HttpClient
) {

    companion object {
        private const val TAG = "UserRemoteDataSource"
    }

    suspend fun sendOTP(phoneNumber: String): UserSendOTPStatus {
        val result = client.post("/auth/send-otp/") {
            setBody(SendOTPDTO(phoneNumber))
        }
        return matchSendOTPStatus(result)
    }

    suspend fun verifyOTP(phoneNumber: String, otp: String): UserVerifyOTPStatus {
        val result = client.post("/auth/verify-otp/") {
            setBody(VerifyOTPDTO("01062024268", otp))
        }
        return mapVerifyOTPStatus(result)
    }

    private suspend fun matchSendOTPStatus(response: HttpResponse): UserSendOTPStatus {
        val status = response.body<Map<String, String>>()["message"] ?: ""
        Log.d(TAG, "matchSendOTPStatus: $status")
        return when (status) {
            "OTP sent successfully" -> UserSendOTPStatus.OTPSent
            "OTP failed to send" -> UserSendOTPStatus.OTPNotSent
            "phone number is not valid" -> UserSendOTPStatus.PhoneNotValid
            else -> throw IllegalStateException(response.bodyAsText())
        }
    }

    private suspend fun mapVerifyOTPStatus(response: HttpResponse): UserVerifyOTPStatus {
        if (response.status in listOf(HttpStatusCode.BadRequest, HttpStatusCode.NotFound)) {
            val message = response.body<Map<String, String>>()["message"]
            return when (message) {
                "Wrong OTP" -> UserVerifyOTPStatus.OTPNotValid
                "OTP is missing" -> UserVerifyOTPStatus.OTPNotValid
                "User not found" -> UserVerifyOTPStatus.UserNotFound
                else -> throw IllegalStateException(response.bodyAsText())
            }
        }

        if (response.status == HttpStatusCode.Created) {
            val tokens = response.body<VerifyOTPResponse>()
            return UserVerifyOTPStatus.OTPVerified(tokens.accessToken, tokens.refreshToken)
        }

        throw IllegalStateException("Unknown status")
    }
}