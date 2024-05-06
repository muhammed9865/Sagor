package com.salman.sagor.presentation.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.salman.sagor.data.repository.FarmRepository
import com.salman.sagor.domain.model.Tank
import com.salman.sagor.presentation.core.MVIViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/3/2024.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val farmRepository: FarmRepository
) : MVIViewModel<HomeAction, HomeState>(HomeState()) {

    private var tanksFetchingJob: Job? = null

    init {
        updatePools()
    }

    override fun handleAction(action: HomeAction) {
        when (action) {
            HomeAction.Reload -> updatePools()
            HomeAction.StopFetching -> {
                tanksFetchingJob?.cancel()
                tanksFetchingJob = null
            }
        }
    }


    private fun updatePools() {
        updateState { copy(isLoading = true) }
        if (tanksFetchingJob?.isActive == true) {
            return
        }
        tanksFetchingJob = farmRepository.getAllTanks()
            .onEach(::updateTanks)
            .launchIn(viewModelScope)
    }

    private fun updateTanks(tanks: List<Tank>) {
        if (tanks.isEmpty()) {
            // Stop fetching data until user reloads
            tanksFetchingJob?.cancel()
            tanksFetchingJob = null
        }
        updateState {
            copy(tanks = tanks, isLoading = false, error = "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("HomeViewModel", "onCleared: ${tanksFetchingJob?.isCancelled}")
        tanksFetchingJob?.cancel()
        tanksFetchingJob = null
    }
}