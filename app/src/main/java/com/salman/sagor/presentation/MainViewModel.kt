package com.salman.sagor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.sagor.data.repository.UserRepository
import com.salman.sagor.presentation.model.MainState
import com.salman.sagor.presentation.model.NavigationParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/29/2024.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val mutableState = MutableStateFlow(MainState())
    val state = mutableState.asStateFlow()

    init {
        loadUserPreferences()
    }

    private fun loadUserPreferences() = viewModelScope.launch {
        val isUserLoggedIn = async { userRepository.isUserLoggedIn() }
        val isOnboardingCompleted = async { userRepository.isOnboardingCompleted() }
        mutableState.value = MainState(
            navigationParams = NavigationParams(
                isUserLoggedIn = isUserLoggedIn.await(),
                isOnboardingComplete = isOnboardingCompleted.await()
            ),
            isSplashVisible = false
        )
    }

}