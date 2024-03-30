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
import com.salman.sagor.presentation.navigation.AppNavigation
import com.salman.sagor.presentation.theme.SagorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create")
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("onNewIntent")
    }
}