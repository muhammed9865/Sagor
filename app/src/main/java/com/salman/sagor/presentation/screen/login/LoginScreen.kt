package com.salman.sagor.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.salman.sagor.R
import com.salman.sagor.presentation.composable.SPrimaryButton
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.navigation.LocalNavigator

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.current

    if (state.isOtpSent) {
        navigator.navigate("verify")
    }

    Screen {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection(modifier = Modifier.weight(0.4f))
            FormSection(
                state = state, onAction = viewModel::handleAction,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Hi, welcome to",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "SAGOR!",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun FormSection(
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit = {}
) {
    val textFieldValue = TextFieldValue(
        state.phoneNumber,
        selection = TextRange(state.phoneNumber.length, state.phoneNumber.length)
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Phone number",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        // Phone number input
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textFieldValue,
            onValueChange = { onAction(LoginAction.PhoneNumberChanged(it.text)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone number",
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            },
            shape = MaterialTheme.shapes.small,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true,
        )
        SPrimaryButton(text = stringResource(R.string.login), isLoading = state.isLoggingIn) {
            onAction(LoginAction.LoginClicked)
        }
    }
}

