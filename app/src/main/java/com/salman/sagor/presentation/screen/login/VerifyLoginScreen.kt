package com.salman.sagor.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.salman.sagor.R
import com.salman.sagor.presentation.composable.SClickableText
import com.salman.sagor.presentation.composable.SPrimaryButton
import com.salman.sagor.presentation.composable.Screen
import com.salman.sagor.presentation.navigation.LocalNavigator

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/30/2024.
 */
@Composable
fun VerifyLoginScreen(
    viewModel: LoginViewModel
) {
    val state by viewModel.state.collectAsState()
    val navigator = LocalNavigator.current
    viewModel.handleAction(LoginAction.NavigatedToVerification)

    VerifyLoginContent(
        state = state,
        onAction = viewModel::handleAction,
        onBackPressed = { navigator.popBackStack() },
    )
}

@Composable
private fun VerifyLoginContent(
    state: LoginState,
    onBackPressed: () -> Unit = {},
    onAction: (LoginAction) -> Unit
) {
    Screen(onBackPressed = onBackPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeaderSection(phoneNumber = state.phoneNumber)
            FormSection(state = state, onAction = onAction)
        }
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    phoneNumber: String,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_verify_illustration),
            contentDescription = "Verify Illustration",
            modifier = Modifier.align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Let's make sure it's you",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A verification code has been sent\nto $phoneNumber",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
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
        state.otp,
        selection = TextRange(state.otp.length, state.otp.length)
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Enter code",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        // Phone number input
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textFieldValue,
            onValueChange = { onAction(LoginAction.OtpChanged(it.text)) },
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
        SPrimaryButton(text = stringResource(R.string.verify), isLoading = state.isLoggingIn) {
            onAction(LoginAction.VerifyOTP)
        }
        SClickableText(
            prefixText = "Didn't get it?", clickableText = " Resend code.",
            modifier = Modifier.align(CenterHorizontally),
        ) {
            onAction(LoginAction.ResendOtpClicked)
        }
    }
}
