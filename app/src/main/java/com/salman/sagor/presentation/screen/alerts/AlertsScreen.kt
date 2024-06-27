package com.salman.sagor.presentation.screen.alerts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.salman.sagor.R
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.navigation.LocalNavigator

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 *  TODO: Add ViewModel and UI logic
 */
@Composable
fun AlertsScreen() {
    val navigator = LocalNavigator.current
    Screen(
        title = stringResource(R.string.alerts),
        onBackPressed = navigator::popBackStack
    ) {
        AlertsContent()
    }
}

@Composable
private fun AlertsContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Nothing here yet.")
    }
}
