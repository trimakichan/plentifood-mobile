package com.example.plentifood.ui.screens.login


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.OutlineTextField
import com.example.plentifood.ui.composables.PrimaryButton

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onClickBack: () -> Unit,
    onNavigateToDashboard: (Int, String) -> Unit
) {

    var username by remember { mutableStateOf("") }
    val loginResponse = viewModel.loginResponse
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    LaunchedEffect(loginResponse) {
        val response = loginResponse ?: return@LaunchedEffect
        onNavigateToDashboard(response.organizationId, response.username)
        viewModel.consumeLoginSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        ButtonWithIcon(
            modifier = Modifier
                .padding(4.dp)
                .size(34.dp),
            Icons.Outlined.ArrowBackIosNew,
            onClickBack,
            description = "Go Back Icon"
        )


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                buildAnnotatedString {
                    append("Welcome\n")
                    append("Back!")
                },
                style = MaterialTheme.typography.titleLarge.copy(
                    lineHeight = 54.sp
                ),
                textAlign = TextAlign.Center,
            )


            Text(
                "Good to see you again!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

        }

        OutlineTextField(
            Icons.Outlined.PermIdentity,
            "Username",
            username,
            onValueChange = {
                username = it
            },
            errorMsg = errorMessage,
            isLoading = isLoading
        )


        PrimaryButton(
            "Login",
            onButtonClick =
                {
                    viewModel.login(username.trim())
                }
        )


    }
}

