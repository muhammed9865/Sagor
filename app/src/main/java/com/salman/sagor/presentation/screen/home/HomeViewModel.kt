package com.salman.sagor.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salman.sagor.domain.model.MetricValueType
import com.salman.sagor.domain.model.Tank
import com.salman.sagor.domain.model.PoolMetric
import com.salman.sagor.presentation.composable.counter.RandomFloat
import com.salman.sagor.presentation.composable.randomColor
import com.salman.sagor.presentation.model.GraphValues
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/3/2024.
 */
class HomeViewModel : ViewModel() {
    private val _progress = RandomFloat(200f)
    val progress = _progress.progress.asStateFlow()
    private val mutablePools = MutableStateFlow<List<Tank>>(emptyList())
    val pools = mutablePools.asStateFlow()


    init {
        updatePools()
        viewModelScope.launch {
            while (isActive)
                _progress.work()
        }
    }

    private fun updatePools() = viewModelScope.launch {
        val pools = List(3) {
            PoolGenerator(it, "Pool ${it + 1}").apply {
                get()
            }
        }
        while (isActive) {
            mutablePools.update {
                pools.map { pool -> pool.update() }
            }
            delay(1000L)
        }
    }


    private class PoolGenerator(val id: Int, val name: String) {
        private var tank: Tank? = null
        val xValues = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        val yValues = listOf(1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 14, 30)
        private val colors by lazy {
            List(3) {
                randomColor()
            }
        }
        private val names = listOf("Oxygen", "pH", "Temperature")

        fun get(): Tank {
            if (tank == null) {
                val newPoints = List(3) {
                    GraphValues(
                        history = getRandomPoints(xValues.size),
                        name = names[it]
                    )
                }
                tank = Tank(
                    id = id,
                    name = name,
                    xValues = xValues,
                    yValues = yValues,
                    history = newPoints,
                    metrics = getMetrics(),
                )
            }

            return tank!!
        }

        fun update(): Tank {
            val newMetricsValues = getMetrics().map { it.value }
            return tank!!.copy(
                history = List(3) {
                    GraphValues(
                        history = getRandomPoints(xValues.size),
                        name = names[it]
                    )
                },
                metrics = tank!!.metrics.mapIndexed { index, poolMetric ->
                    poolMetric.copy(value = newMetricsValues[index])
                }
            )
        }

        private fun getMetrics(): List<PoolMetric> {
            return List(3) {
                PoolMetric(
                    name = names[it],
                    value = Random.nextDouble(0.0, 200.0).toFloat(),
                    maxValue = 200f,
                    boundaryValues = listOf(0, 75, 125, 200),
                    valueType = listOf(MetricValueType.Progress, MetricValueType.Text).random()
                )
            }
        }

        private fun getRandomPoints(size: Int): List<Float> {
            return List(size) {
                Random.nextDouble(0.0, 50.0).toFloat()
            }
        }
    }
}