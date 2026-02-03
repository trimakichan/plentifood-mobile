package com.example.plentifood.ui.screens.site

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.PhoneInTalk
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.R
import com.example.plentifood.ui.composables.MapSection
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.example.plentifood.data.models.site.Site
import com.example.plentifood.ui.composables.DirectionButton
import com.example.plentifood.ui.composables.HoursRow
import com.example.plentifood.ui.composables.InfoRow


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SiteDetailScreen(
    siteId: Int,
    viewModel: SiteDetailViewModel = viewModel(),
    onClickBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.fetchSite(siteId)
    }

    val isLoading = viewModel.isLoading
    val site = viewModel.site

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    fun cleanAddress(site: Site): List<String> =
        listOfNotNull(
            site.addressLine1,
            site.addressLine2,
            site.city,
            site.state,
            site.postalCode
        )




    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { ContainedLoadingIndicator(
            containerColor = MaterialTheme.colorScheme.tertiary,
            indicatorColor = MaterialTheme.colorScheme.primary,
        )}

    } else {
        site?.let { site ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {


                    Box {
                        MapSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            sites = listOf(site),
                            selectedSite = site,
                            onSiteSelected = {},
                            userLat = 0.0,
                            userLon = 0.0
                        )

                        Icon(
                            painter = painterResource(R.drawable.back),
                            contentDescription = "Go Back Icon",
                            modifier = Modifier
                                .clickable { onClickBack() }
                                .padding(14.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(6.dp)
                                .size(32.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                    HorizontalDivider()

                    Column(
                        modifier = Modifier
//                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .verticalScroll(scrollState)
                            .padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    )
                    {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(
                                site.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                            )

                            site.organizationWebsiteUrl?.let {
                                Icon(
                                    imageVector = Icons.Outlined.Language,
                                    contentDescription = "Website Link",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                                            context.startActivity(intent)
                                        },
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                        }


                        InfoRow(
                            Icons.Outlined.Settings,
                            "Services",
                            site.services.map { it.name },
                            badge = true
                        )
                        InfoRow(
                            Icons.Outlined.PermIdentity,
                            "Eligibility",
                            listOf(site.eligibility),
                            badge = true
                        )

                        InfoRow(
                            Icons.Outlined.LocationOn,
                            "Address",
                            cleanAddress(site)
                        )

                        InfoRow(
                            Icons.Outlined.PhoneInTalk,
                            "Phone",
                            listOf(site.phone),
                        )

                        HoursRow(
                            Icons.Outlined.WatchLater,
                            "Hours",
                            site.hours
                        )


                        Text(
                            "Notes: ",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            site.serviceNotes,
                            style = MaterialTheme.typography.bodyMedium

                        )

                    }


                }

                DirectionButton(
                    onClick = {
                        val uri = "google.navigation:q=${site.latitude},${site.longitude}".toUri()
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        // Force Google Maps if installed (optional)
                        intent.setPackage("com.google.android.apps.maps")
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .navigationBarsPadding()
                        .padding(16.dp)
                )
            }

        }


    }
}














