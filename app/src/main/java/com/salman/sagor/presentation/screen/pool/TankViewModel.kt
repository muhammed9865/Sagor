package com.salman.sagor.presentation.screen.pool

import androidx.lifecycle.viewModelScope
import com.salman.sagor.data.repository.FarmRepository
import com.salman.sagor.domain.model.Tank
import com.salman.sagor.presentation.core.MVIViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/2/2024.
 */
@HiltViewModel
class TankViewModel @Inject constructor(
    private val farmRepository: FarmRepository
) : MVIViewModel<TankAction, TankState>(TankState()) {

    override fun handleAction(action: TankAction) {
        when (action) {
            is TankAction.LoadTank -> loadTank(action.tankId)
        }
    }

    private fun loadTank(tankId: Int) = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        farmRepository.getTankDetails(tankId)
            .onEach(::updateTankDetails)
            .launchIn(viewModelScope)
    }

    private fun updateTankDetails(tank: Tank?) {
        if (tank == null) {
            updateState {
                copy(
                    failedToLoadDetails = true, isLoading = false,
                )
            }
        } else {
            updateState {
                copy(
                    name = tank.name,
                    currentReadings = tank.packages.first().currentReadings,
                    sensorsHistory = tank.packages.first().sensorsHistory,
                    isLoading = false,
                    lastUpdated = tank.packages.first().lastUpdated
                )
            }
        }
    }
}