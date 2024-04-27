package com.salman.sagor.data.source.user

import com.salman.sagor.data.source.RemoteConstants.BASE_URL
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
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
class UserRemoteDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun sendOTP(phoneNumber: String): UserSendOTPStatus {
        val result = client.post("$BASE_URL/user/verify-otp/") {
            setBody(SendOTPDTO(phoneNumber))
        }
        return matchSendOTPStatus(result)
    }

    suspend fun verifyOTP(phoneNumber: String, otp: String): UserVerifyOTPStatus {
        val result = client.post("$BASE_URL/user/verify-otp/") {
            setBody(VerifyOTPDTO(phoneNumber, otp))
        }
        return mapVerifyOTPStatus(result)
    }

    private suspend fun matchSendOTPStatus(response: HttpResponse): UserSendOTPStatus {
        val status = response.body<Map<String, String>>()["message"] ?: ""
        return when (status) {
            "OTP sent successfully" -> UserSendOTPStatus.OTPSent
            "OTP failed to send" -> UserSendOTPStatus.OTPNotSent
            "phone number is not valid" -> UserSendOTPStatus.PhoneNotValid
            else -> throw IllegalStateException("Unknown status")
        }
    }

    private suspend fun mapVerifyOTPStatus(response: HttpResponse): UserVerifyOTPStatus {
        if (response.status in listOf(HttpStatusCode.BadRequest, HttpStatusCode.NotFound)) {
            val message = response.body<Map<String, String>>()["message"]
            return when (message) {
                "Wrong OTP" -> UserVerifyOTPStatus.OTPNotValid
                "OTP is missing" -> UserVerifyOTPStatus.OTPNotValid
                "User not found" -> UserVerifyOTPStatus.UserNotFound
                else -> throw IllegalStateException("Unknown message")
            }
        }

        if (response.status == HttpStatusCode.Created) {
            val tokens = response.body<VerifyOTPResponse>()
            return UserVerifyOTPStatus.OTPVerified(tokens.accessToken, tokens.refreshToken)
        }

        throw IllegalStateException("Unknown status")
    }
}