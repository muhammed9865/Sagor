package com.salman.sagor.data.repository

import com.salman.sagor.data.source.user.UserLocalDataSource
import com.salman.sagor.data.source.user.UserRemoteDataSource
import com.salman.sagor.domain.model.User
import com.salman.sagor.domain.model.UserSendOTPStatus
import com.salman.sagor.domain.model.UserVerifyOTPStatus
import kotlinx.coroutines.Dispatchers
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

        return@withContext runCatching {
            val isSent = userRemoteDataSource.sendOTP(phoneNumber) is UserSendOTPStatus.OTPSent
            if (isSent.not()) {
                throw Exception("OTP not sent")
            }
        }.also {
            if (it.isFailure) {
                it.exceptionOrNull()!!.printStackTrace()
            }
        }
    }

    suspend fun verifyOTP(phoneNumber: String, otp: String): Result<Unit> {
        val verificationStatus = userRemoteDataSource.verifyOTP(phoneNumber, otp)
        return runCatching {
            when (verificationStatus) {
                is UserVerifyOTPStatus.OTPNotValid -> throw Exception("OTP is not valid")
                is UserVerifyOTPStatus.UserNotFound -> throw Exception("User not found")
                is UserVerifyOTPStatus.OTPVerified -> {
                    userLocalDataSource.accessToken = verificationStatus.accessToken
                    userLocalDataSource.refreshToken = verificationStatus.refreshToken
                }
            }
        }
    }

    suspend fun isUserLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        userLocalDataSource.accessToken != null
    }

    suspend fun isOnboardingCompleted(): Boolean = withContext(Dispatchers.IO) {
        userLocalDataSource.onboardingCompleted
    }

    suspend fun completeOnboarding() = withContext(Dispatchers.IO) {
        userLocalDataSource.onboardingCompleted = true
    }
}