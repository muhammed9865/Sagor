package com.salman.sagor.data.repository

import com.salman.sagor.data.source.user.UserLocalDataSource
import com.salman.sagor.data.source.user.UserRemoteDataSource
import com.salman.sagor.domain.model.User
import com.salman.sagor.domain.model.UserSendOTPStatus
import com.salman.sagor.domain.model.UserVerifyOTPStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) {

    var currentUser: User? = null
        private set

    /**
     * Should return the OTP sent to the user
     */
    suspend fun login(phoneNumber: String): Result<Unit> = withContext(Dispatchers.IO) {
        if (isUserLoggedIn())
            return@withContext Result.success(Unit)

        val isSent = userRemoteDataSource.sendOTP(phoneNumber) is UserSendOTPStatus.OTPSent
        return@withContext if (isSent) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Failed to send OTP"))
        }
    }

    suspend fun verifyOTP(phoneNumber: String, otp: String): Result<Unit> {
        val verificationStatus = userRemoteDataSource.verifyOTP(phoneNumber, otp)
        return when (verificationStatus) {
            is UserVerifyOTPStatus.OTPNotValid -> Result.failure(Exception("OTP is not valid"))
            is UserVerifyOTPStatus.UserNotFound -> Result.failure(Exception("User not found"))
            is UserVerifyOTPStatus.OTPVerified -> {
                userLocalDataSource.accessToken = verificationStatus.accessToken
                userLocalDataSource.refreshToken = verificationStatus.refreshToken
                Result.success(Unit)
            }
        }
    }

    suspend fun isUserLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        userLocalDataSource.accessToken != null
    }
}