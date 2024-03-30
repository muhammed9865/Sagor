package com.salman.sagor.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.salman.sagor.presentation.navigation.AppNavigation
import com.salman.sagor.presentation.theme.SagorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            SagorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val intent = remember {
                        mutableStateOf<Intent?>(null)
                    }

                    DisposableEffect(Unit) {
                        val listener = { newIntent: Intent ->
                            intent.value = newIntent
                        }
                        addOnNewIntentListener(listener)

                        onDispose {
                            removeOnNewIntentListener(listener)
                        }
                    }
                    AppNavigation(intent.value)
                }
            }
        }
    }
}