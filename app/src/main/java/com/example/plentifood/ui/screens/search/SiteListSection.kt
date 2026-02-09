package com.example.plentifood.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plentifood.data.models.response.Site
import com.example.plentifood.ui.composables.InfoCard

@Composable
fun SiteListSection (
    sites: List<Site>,
    onclickSiteDetail: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        items(sites) { site ->
            InfoCard(
                modifier = Modifier,
                site,
                onClickSiteDetail = { onclickSiteDetail(site.id) }
            )
        }
    }

    }