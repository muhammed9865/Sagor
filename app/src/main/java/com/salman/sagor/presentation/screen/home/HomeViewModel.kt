package com.salman.sagor.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.sagor.presentation.composable.counter.RandomFloat
import com.salman.sagor.presentation.composable.randomColor
import com.salman.sagor.presentation.model.GraphValues
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/3/2024.
 */
class GraphViewModel : ViewModel() {
    private val _lines = MutableStateFlow<List<GraphValues>>(emptyList())
    val lines = _lines.asStateFlow()
    private val _progress = RandomFloat(200f)
    val progress = _progress.progress.asStateFlow()

    val xValues = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    val yValues = listOf(1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 14, 30)

    private val colors by lazy {
        List(3) {
            randomColor()
        }
    }

    init {
        updateLines()
        viewModelScope.launch {
            while (isActive)
                _progress.work()
        }
    }

    private fun updateLines() = viewModelScope.launch {
        while (isActive) {
            val newPoints = List(3) {
                GraphValues(
                    values = getRandomPoints(xValues.size),
                    color = colors[it]
                )
            }
            _lines.update {
                newPoints
            }
            delay(1000L)
        }
    }


}