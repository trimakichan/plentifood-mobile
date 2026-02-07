package com.example.plentifood.ui.screens.dashboard


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.DayHours
import com.example.plentifood.ui.composables.InfoCard
import com.example.plentifood.ui.composables.MultiSelectedButton
import com.example.plentifood.ui.composables.OutlineTextField
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.screens.login.LoginViewModel
import com.example.plentifood.ui.utils.toTitleFromSnakeCase

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AdminDashboardScreen(
    viewModel: AdminDashboardViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    organizationId: Int,
    username: String,
    onClickHome: () -> Unit,
    onClickSiteDetail: (Int) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.fetchOrganization(organizationId)
    }

    val isLoading = viewModel.isLoading
    val organizationResponse = viewModel.organizationResponse
    val errorMessage = viewModel.errorMessage

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.submitSuccess) {
        if (viewModel.submitSuccess) {
            viewModel.fetchOrganization(organizationId)
            showDialog = false
            viewModel.submitSuccess = false
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ButtonWithIcon(
                    modifier = Modifier.size(44.dp),
                    icon = Icons.Outlined.Home,
                    onClick = onClickHome,
                    description = "Go Home Icon"
                )


                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    "Dashboard",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            TextButton(onClick = {
                loginViewModel.logout()
                onClickHome()
            }) {
                Text(
                    "Logout",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }

        Spacer(modifier = Modifier.size(16.dp))

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("Hi, ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(username) }
                    append(" !")
                },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )


            Spacer(modifier = Modifier.size(16.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        ContainedLoadingIndicator(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                organizationResponse != null -> {

                    Text(
                        "Organization: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(24.dp),

                        verticalArrangement = Arrangement.spacedBy(12.dp),

                        ) {

                        Row() {
                            Text(
                                "Name: ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                organizationResponse.name,
                                style = MaterialTheme.typography.bodyMedium,

                                )
                        }


                        Row() {
                            Text(
                                "Organization Type: ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold

                            )

                            Text(
                                organizationResponse.organizationType.toTitleFromSnakeCase(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }


                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Sites: ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        ButtonWithIcon(
                            modifier = Modifier
                                .width(48.dp)
                                .padding(8.dp)
                                .background(
                                    MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            icon = Icons.Outlined.Add,
                            onClick = { showDialog = true },
                            description = "Add Site Button"
                        )

                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        items(organizationResponse.sites) { site ->
                            InfoCard(
                                modifier = Modifier,
                                site,
                                onClickSiteDetail = {
                                    onClickSiteDetail(site.id)
                                }
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        text = "No organization data found.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    }


    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false })
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
    onDismissRequest: () -> Unit,
    viewModel: AdminDashboardViewModel = viewModel()
) {

    val days = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )

    val site = viewModel.uiState
    val isValid =
        site.siteName.isNotBlank() &&
                site.address1.isNotBlank() &&
                site.city.isNotBlank() &&
                site.state.isNotBlank() &&
                site.postalCode.isNotBlank() &&
                site.phone.isNotBlank() &&
                site.eligibility.isNotBlank() &&          // IMPORTANT (see #2)
                site.serviceTypes.isNotEmpty()

    Dialog(
        onDismissRequest = { onDismissRequest() },
        DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Site Registration",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        actions = {
                            ButtonWithIcon(
                                modifier = Modifier.size(44.dp),
                                icon = Icons.Outlined.Close,
                                onClick = onDismissRequest,
                                description = "Close Icon"
                            )
                        }


                    )
                }

            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(padding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        "Please provide the details below to register a site",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )


                    Text(
                        "* Required fields",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlineTextField(
                            Icons.Outlined.HomeWork,
                            "Site Name *",
                            viewModel.uiState.siteName,
                            onValueChange = {
                                viewModel.updateSiteName(it)
                            },
                        )

                        OutlineTextField(
                            Icons.Outlined.LocationOn,
                            "Address 1 *",
                            viewModel.uiState.address1,
                            onValueChange = {
                                viewModel.updateAddress1(it)
                            },
                        )

                        OutlineTextField(
                            Icons.Outlined.LocationOn,
                            "Address 2 (optional)",
                            viewModel.uiState.address2,
                            onValueChange = {
                                viewModel.updateAddress2(it)
                            },
                        )

                        OutlineTextField(
                            Icons.Outlined.LocationOn,
                            "City *",
                            viewModel.uiState.city,
                            onValueChange = {
                                viewModel.updateCity(it)
                            },
                        )

                        OutlineTextField(
                            Icons.Outlined.LocationOn,
                            "State *",
                            viewModel.uiState.state,
                            onValueChange = {
                                viewModel.updateState(it)
                            },
                        )

                        OutlineTextField(
                            Icons.Outlined.LocationOn,
                            "Postal Code *",
                            viewModel.uiState.postalCode,
                            onValueChange = {
                                viewModel.updatePostalCode(it)
                            },
                        )

                        OutlineTextField(
                            Icons.Outlined.Phone,
                            "Phone Number *",
                            viewModel.uiState.phone,
                            onValueChange = {
                                viewModel.updatePhone(it)
                            },
                        )
                    }


                    Column {
                        Text(
                            "Eligibility *",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        DropdownMenuEligibility(
                            selectedOption = viewModel.uiState.eligibility.toTitleFromSnakeCase(),
                            onOptionSelected = {
                                viewModel.updateEligibility(it)
                            }
                        )
                    }


                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "Service Types *",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        MultiSelectedButton(
                            viewModel.serviceTypes,
                            viewModel.serviceTypesChecked,
                            onCheckedChange = { index, value ->
                                viewModel.serviceTypesChecked[index] = value
                                viewModel.updateServiceTypes()
                            })
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Operating hours: ",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            "NOTE: Select times for days the site is open. Leave blank if closed.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )


                        days.forEach { day ->
                            DayHours(
                                day = day,
                                { day, time -> viewModel.updateOpen(day, time) },
                                { day, time -> viewModel.updateClose(day, time) }
                            )
                        }

                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Add any notes (optional)",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )

                        OutlinedTextField(
                            value = viewModel.uiState.notes,
                            onValueChange = { viewModel.updateNotes(it) },
                            label = { Text("Service Notes") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    PrimaryButton(
                        "Add Site",
                        onButtonClick = {
                            viewModel.organizationResponse?.id?.let { orgId ->
                                viewModel.submitAddSite(orgId)
                            }
//                            if success -> show dialog

                        },
                        enable = isValid && !viewModel.isSubmitLoading
                    )

                }
            }
        }
    }
}


// refactor this and move it to composable later
@Composable
fun DropdownMenuEligibility(
    selectedOption: String? = null,
    onOptionSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption ?: "Select Eligibility *",
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
                text = { Text("General Public") },
                onClick = {
                    onOptionSelected("General Public")
                    expanded = false
                }
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = { Text("Older Adults and Eligible") },
                onClick = {
                    onOptionSelected("Older Adults and Eligible")
                    expanded = false
                }
            )

            HorizontalDivider()

            // Second section
            DropdownMenuItem(
                text = { Text("Youth Young Adults") },
                onClick = {
                    onOptionSelected("Youth Young Adults")
                    expanded = false
                }
            )
        }
    }
}