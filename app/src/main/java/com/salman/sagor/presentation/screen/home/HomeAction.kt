package com.salman.sagor.presentation.screen.home

import com.salman.sagor.presentation.core.UiAction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/2/2024.
 */
sealed class HomeAction : UiAction {
    data object Reload : HomeAction()
    data object StopFetching : HomeAction()
}