package com.salman.sagor.presentation.screen.onboarding

import com.salman.sagor.presentation.core.UiAction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
sealed class OnboardingAction: UiAction {

    data object OnNext: OnboardingAction()

    data class OnPageChanged(val page: Int): OnboardingAction()

    data object OnFinish: OnboardingAction()
}