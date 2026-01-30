package com.example.plentifood.ui.screens.site

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SiteDetailScreen (
    siteId: Int,
    viewModel: SiteDetailViewModel = viewModel(),

) {

    LaunchedEffect(Unit) {
        viewModel.fetchSite(siteId)
    }

    val isLoading = viewModel.isLoading
    val site = viewModel.site

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    } else {
        site?.let {
            Text(it.name)
        }
        Text("Site Detail Page $siteId")

    }
}