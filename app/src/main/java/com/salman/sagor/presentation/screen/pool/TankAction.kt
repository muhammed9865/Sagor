package com.salman.sagor.presentation.screen.pool

import com.salman.sagor.presentation.core.UiAction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/2/2024.
 */
sealed class TankAction : UiAction {

    data class LoadTank(val tankId: Int) : TankAction()
}