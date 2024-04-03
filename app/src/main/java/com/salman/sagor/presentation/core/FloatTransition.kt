package com.salman.sagor.presentation.core

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.salman.sagor.presentation.model.GraphTransition
import kotlin.math.max

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/3/2024.
 */
@Composable
fun animateListOfFloats(
    targetFractions: List<Float>
): GraphTransition {
    val mutableState = remember { MutableTransitionState(emptyList<Float>()) }
    mutableState.targetState = targetFractions
    val transition = rememberTransition(mutableState, label = "main-animation")

    val maxFractionsSize = max(transition.currentState.size, targetFractions.size)
    val values = (0 until maxFractionsSize).map { index ->
        transition.animateFloat(label = "fraction-$index-animation") { state ->
            state.getOrNull(index) ?: 0f
        }
    }

    val animatedFractions = remember(transition) { SnapshotStateList<State<Float>>() }
    remember(values) {
        animatedFractions.clear()
        animatedFractions.addAll(values)
    }
    return remember(transition) { GraphTransition(animatedFractions) }
}