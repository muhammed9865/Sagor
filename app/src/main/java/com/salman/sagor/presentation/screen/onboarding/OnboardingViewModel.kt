package com.salman.sagor.presentation.screen.onboarding

import androidx.lifecycle.viewModelScope
import com.salman.sagor.data.repository.UserRepository
import com.salman.sagor.presentation.core.MVIViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : MVIViewModel<OnboardingAction, OnboardingState>(OnboardingState()) {

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
                completeOnboarding()
            }
        }
    }

    private fun next() = viewModelScope.launch {
        val state = state.value
        if (state.currentPage < state.onboardingPages.size - 1) {
            updateState { copy(currentPage = currentPage.inc()) }
        } else {
            userRepository.completeOnboarding()
            updateState { copy(onboardingFinished = true) }
        }
    }

    private fun completeOnboarding() = viewModelScope.launch {
        userRepository.completeOnboarding()
        updateState { copy(onboardingFinished = true) }
    }
}