package com.salman.sagor.data.source.user

import android.content.Context
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
class UserLocalDataSource @Inject constructor(
    context: Context,
) {

    companion object {
        private const val PREF_NAME = "user_pref"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var accessToken: String?
        get() = pref.getString(KEY_ACCESS_TOKEN, null)
        set(value) {
            pref.edit().putString(KEY_ACCESS_TOKEN, value).apply()
        }

    var refreshToken: String?
        get() = pref.getString(KEY_REFRESH_TOKEN, null)
        set(value) {
            pref.edit().putString(KEY_REFRESH_TOKEN, value).apply()
        }

    var onboardingCompleted: Boolean
        get() = pref.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) {
            pref.edit().putBoolean(KEY_ONBOARDING_COMPLETED, value).apply()
        }
}