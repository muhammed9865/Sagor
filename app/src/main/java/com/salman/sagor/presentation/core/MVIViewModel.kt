package com.salman.sagor.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/22/2024.
 */
abstract class MVIViewModel<in Action : UiAction, State : UiState>(
    initialState: State
) : ViewModel() {

    private val mutableState = MutableStateFlow(initialState)
    val state = mutableState.asStateFlow()

    abstract fun handleAction(action: Action)

    protected fun updateState(reducer: State.() -> State) {
        mutableState.update(reducer)
    }

    protected fun updateTemporarilyState(
        delay: Long = 100,
        resetState: State? = null,
        reducer: State.() -> State
    ) {
        val currState = resetState ?: mutableState.value
        mutableState.update(reducer)
        viewModelScope.launch {
            delay(delay)
            mutableState.update { currState }
        }
    }

}