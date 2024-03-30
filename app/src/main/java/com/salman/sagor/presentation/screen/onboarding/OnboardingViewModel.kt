package com.salman.sagor.presentation.screen.onboarding

import com.salman.sagor.presentation.core.MVIViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(): MVIViewModel<OnboardingAction, OnboardingState>(OnboardingState()) {

    override fun handleAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.OnNext -> {
                next()
            }

            is OnboardingAction.OnPageChanged -> {
                println(action.page)
                updateState { copy(currentPage = action.page) }
            }

            is OnboardingAction.OnFinish -> {
                updateState { copy(onboardingFinished = true) }
            }
        }
    }

    private fun next() {
        val state = state.value
        if (state.currentPage < state.onboardingPages.size - 1) {
            updateState { copy(currentPage = currentPage.inc()) }
        } else {
            updateState { copy(onboardingFinished = true) }
        }
    }
}