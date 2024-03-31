package com.salman.sagor.data.repository

import com.salman.sagor.data.model.User
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
class UserRepository @Inject constructor() {

    var currentUser: User? = null
        private set

    /**
     * Should return the OTP sent to the user
     */
    suspend fun login(phoneNumber: String): Result<String> {
        delay(2000)
        currentUser = User(1, "token", "Salman", phoneNumber)
        return "123456".let { Result.success(it) }
    }

    suspend fun resendOTP(phoneNumber: String): Result<Unit> {
        delay(2000)
        return Result.success(Unit)
    }
}