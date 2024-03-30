package com.salman.sagor.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.salman.sagor.R

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
sealed class OnboardingPage(
    @StringRes val description: Int,
    @DrawableRes val image: Int
) {
    companion object Companion {
        operator fun get(index: Int): OnboardingPage {
            return when (index) {
                0 -> OnboardingPage1
                1 -> OnboardingPage2
                2 -> OnboardingPage3
                else -> throw IndexOutOfBoundsException()
            }
        }

        data object OnboardingPage1 : OnboardingPage(
            description = R.string.onboarding_1_description,
            image = R.drawable.onboard1
        )

        data object OnboardingPage2 : OnboardingPage(
            description = R.string.onboarding_2_description,
            image = R.drawable.onboard2
        )

        data object OnboardingPage3 : OnboardingPage(
            description = R.string.onboarding_3_description,
            image = R.drawable.onboard3
        )
    }
}
