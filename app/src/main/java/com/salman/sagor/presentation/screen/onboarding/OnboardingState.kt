package com.salman.sagor.presentation.screen.onboarding

import com.salman.sagor.presentation.core.UiState
import com.salman.sagor.presentation.model.OnboardingPage

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
data class OnboardingState(
    val currentPage: Int = 0,
    val onboardingFinished: Boolean = false
) : UiState {

    val onboardingPages = listOf(
        OnboardingPage.Companion.OnboardingPage1,
        OnboardingPage.Companion.OnboardingPage2,
        OnboardingPage.Companion.OnboardingPage3
    )
}
