package com.example.plentifood.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plentifood.data.models.response.Site
import com.example.plentifood.ui.composables.InfoCard
import com.example.plentifood.ui.composables.MapSection

@Composable
fun SiteMapSection(
    sites: List<Site>,
    selectedSite: Site?,
    onSiteSelected: (Site?) -> Unit,
    lat: Double,
    lon: Double,
    onClickSiteDetail: (Int) -> Unit
    ) {
    Box {
        MapSection(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            sites = sites,
            selectedSite = selectedSite,
            onSiteSelected = { clickedSite ->
                onSiteSelected(clickedSite)
            },
            lat,
            lon,
        )

        selectedSite?.let { site ->
            InfoCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                site = site,
                onClickSiteDetail = { onClickSiteDetail(site.id) }
            )
        }
    }
}