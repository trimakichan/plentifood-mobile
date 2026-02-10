package com.example.plentifood.ui.screens.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.DayHours
import com.example.plentifood.ui.composables.MultiSelectedButton
import com.example.plentifood.ui.composables.OutlineTextField
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.utils.toTitleFromSnakeCase


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteRegistrationDialog(
    onDismissRequest: () -> Unit,
    viewModel: AdminDashboardViewModel,
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
                site.eligibility.isNotBlank() &&
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


                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Eligibility *",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        DropdownMenuEligibility(
                            selectedOption = viewModel.uiState.eligibility.toTitleFromSnakeCase(),
                            onOptionSelected = {
                                viewModel.updateEligibility(it)
                            }
                        )
                    }


                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Service Types *",
                            style = MaterialTheme.typography.bodyMedium,
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

                        Row () {
                            Column() {
                                Text(
                                    "Operating hours: ",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Text(
                                    "NOTE: Select times only for days the site is open.",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

//                            Text(
//                                "Reset",
//                                style = MaterialTheme.typography.bodyMedium.copy(
//                                    color = MaterialTheme.colorScheme.error,
//                                    textDecoration = TextDecoration.Underline
//                                ),
//                                modifier = Modifier
//                                    .clickable { viewModel.resetHoursByDay() }
//                                    .testTag("home_sign_up_button"),
//                            )

                        }

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
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        OutlinedTextField(
                            value = viewModel.uiState.notes,
                            onValueChange = { viewModel.updateNotes(it) },
                            label = {
                                Text(
                                    "Service Notes",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    PrimaryButton(
                        "Add Site",
                        onButtonClick = {
                            viewModel.organizationResponse?.id?.let { orgId ->
                                viewModel.submitAddSite(orgId)
                            }
                        },
                        enable = isValid && !viewModel.isSubmitLoading
                    )

                }
            }
        }
    }
}


