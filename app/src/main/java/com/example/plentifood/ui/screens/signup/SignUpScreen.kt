package com.example.plentifood.ui.screens.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.OutlineTextField
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.composables.SecondaryButton

@SuppressLint("SuspiciousIndentation")
@Composable
fun SignUpScreen(
    onClickBack: () -> Unit,
    onClickStaffLogin: () -> Unit,
    onNavigateToDashboard: (Int, String) -> Unit,
    viewModel: SignUpViewModel = viewModel(),
) {

    var username by remember { mutableStateOf("") }
    var organizationName by remember { mutableStateOf("") }
    var websiteUrl by remember { mutableStateOf("") }
    var organizationType by remember { mutableStateOf<String?>(null) }

    val registerResponse = viewModel.registerResponse
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage


    LaunchedEffect(registerResponse) {
      val response = registerResponse ?: return@LaunchedEffect
        onNavigateToDashboard(response.organization.id, response.adminUser.username)
        viewModel.consumeRegisterSuccess()
    }

    fun isFormValid(): Boolean {
        return username.isNotBlank()
                && organizationName.isNotBlank()
                && organizationType != null
    }


    fun onOptionSelected(option: String) {
        organizationType = option
    }

    if (isLoading) {
        Text("Loading...")
    }

    errorMessage?.let {
        Text(it, color = MaterialTheme.colorScheme.error)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {


        ButtonWithIcon(
            modifier = Modifier
                .padding(4.dp)
                .size(34.dp),
            Icons.Outlined.ArrowBackIosNew,
            onClickBack,
            description = "Go Back Icon"
        )

        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Sign Up",
                style = MaterialTheme.typography.titleLarge,

            )

            Text(
                "Please provide the details below to create an account",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center

            )
        }

        Column {
            Text(
                "* Required fields",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.primary
            )

            OutlineTextField(
                Icons.Outlined.PermIdentity,
                "Username *",
                username,
                onValueChange = {
                    username = it
                },
//                errorMsg = if(isRegisterClicked && username.isEmpty()) usernameErrorMsg else "" ,
//                isLoading = isLoading
            )

            OutlineTextField(
                Icons.Outlined.HomeWork,
                "Organization Name *",
                organizationName,
                onValueChange = {
                    organizationName = it
                },
//                errorMsg = errorMessage,
//                isLoading = isLoading
            )

            OutlineTextField(
                Icons.Outlined.Language,
                "Website URL",
                websiteUrl,
                onValueChange = {
                   websiteUrl = it
                },
//                errorMsg = errorMessage,
//                isLoading = isLoading
            )

            DropdownMenuWithDetails(
                organizationType,
                onOptionSelected = {
                    onOptionSelected(it)
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PrimaryButton(
                "Register",
                onButtonClick = {
//                    val orgType = organizationType ?: return@PrimaryButton
                    if (isFormValid()) {
                        viewModel.register(
                            organizationName,
                            // refactor this later
                            organizationType!!,
                            websiteUrl,
                            username
                        )
                    }
                },
                enable = isFormValid() && !viewModel.isLoading
            )


            SecondaryButton(
                "Staff Login",
                modifier = Modifier,
                onButtonClick = { onClickStaffLogin() }
            )
        }


    }
}


@Composable
fun DropdownMenuWithDetails(
    selectedOption: String? = null,
    onOptionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption ?: "Select Organization Type *",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.secondary)
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            )

        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
//                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {

            DropdownMenuItem(
                text = { Text("Food Bank") },
                onClick = {
                    onOptionSelected("Food Bank")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Church") },
                onClick = {
                    onOptionSelected("Church")
                    expanded = false
                }
            )

            HorizontalDivider()

            // Second section
            DropdownMenuItem(
                text = { Text("Nonprofit") },
                onClick = {
                    onOptionSelected("Nonprofit")
                    expanded = false
                 }
            )

            HorizontalDivider()

            // Third section
            DropdownMenuItem(
                text = { Text("Community Center") },
                onClick = {
                    onOptionSelected("Community Center")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Others") },
                onClick = {
                    onOptionSelected("Others")
                    expanded = false
                }
            )
        }
    }
}

//@Preview(showBackground= true)
//@Composable
//fun SignUpPreview() {
//    SignUpScreen(
//        onClickBack = {},
//        {  }
//    )
//}
//
//
////