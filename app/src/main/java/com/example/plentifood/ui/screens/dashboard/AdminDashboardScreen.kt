package com.example.plentifood.ui.screens.dashboard


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.CardDialog
import com.example.plentifood.ui.composables.InfoCard
import com.example.plentifood.ui.composables.MessageOnlyDialog
import com.example.plentifood.ui.screens.login.LoginViewModel
import com.example.plentifood.ui.utils.toTitleFromSnakeCase
import kotlinx.coroutines.delay

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
    var showCardDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteSiteId by remember { mutableStateOf<Int?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }


    LaunchedEffect(viewModel.submitSuccess) {
        if (viewModel.submitSuccess) {
            viewModel.fetchOrganization(organizationId)
            showDialog = false
            showCardDialog = true
            viewModel.submitSuccess = false
        }
    }

    LaunchedEffect(viewModel.deleteSuccess) {
        if (viewModel.deleteSuccess) {
            showSuccessDialog = true
            delay(2_000)
            showSuccessDialog = false
            viewModel.clearDeleteSuccess()
        }
    }

//    fun updateShowDeleteDialog() {
//        showDeleteDialog = true
//    }

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

                    if (organizationResponse.sites.isEmpty()) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Text("Add a site with the + button.",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.secondary
                            )

                        }

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
                                },
                                enableDelete = true,
                                onClickDelete = {
                                    showDeleteDialog = true
                                    deleteSiteId = site.id
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
        SiteRegistrationDialog(
            onDismissRequest = { showDialog = false },
            viewModel = viewModel
        )
    }

    if (showCardDialog) {
        CardDialog(
            onDismissRequest = { showCardDialog = false },
            onConfirmation = { showCardDialog = false },
            dialogTitle = "Site Registration",
            dialogText = "Site was registered successfully\uD83C\uDF89",
            icon = Icons.Outlined.CheckCircle,
            enableDismissButton = false
        )
    }

    if (showDeleteDialog && deleteSiteId != null) {
        CardDialog(
            onDismissRequest = {
                showDeleteDialog = false
                deleteSiteId = null
            },
            onConfirmation = {
                deleteSiteId?.let { siteId ->
                    viewModel.deleteSite(siteId, organizationId)
                    showDeleteDialog = false
                    deleteSiteId = null
                }
            },
            dialogTitle = "Delete Site",
            dialogText = "Are you sure you want to delete this site?",
            icon = Icons.Outlined.Delete,
            enableDismissButton = true,
        )
    }

    if (showSuccessDialog) {
        MessageOnlyDialog(
            onDismissRequest = { },
            message = "Site was deleted successfully!"
        )
    }
}
