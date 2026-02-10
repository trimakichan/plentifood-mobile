package com.example.plentifood.ui.screens.search


import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.plentifood.data.models.response.Site
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import androidx.compose.ui.platform.testTag

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = viewModel(),
    onClickSiteDetail: (Int) -> Unit,
    onClickFilterButton: () -> Unit,
    onClickBack: () -> Unit
) {

    val context = LocalContext.current

    val placesClient: PlacesClient? = remember {
        val isPreview = false
        if (isPreview) null else Places.createClient(context)
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // ---- viewModel state / actions ----
    val searchQuery = viewModel.searchQuery
    val predictions = viewModel.predictions
    val onSearchQueryChange = viewModel::onSearchQueryChange
    val fetchPredictions = viewModel::fetchPredictions
    val fetchPlaceLatLng = viewModel::fetchPlaceLatLng
    val resetSearch = viewModel::resetSearch

    val isLoading = viewModel.isLoading
    val totalResults = viewModel.totalResults
    val sites = viewModel.sites
    val numOfFilters = viewModel.numOfFilters.toString()

    val lat = viewModel.lat
    val lon = viewModel.lon
    val radiusMiles = viewModel.radiusMiles
    val days = viewModel.days.toList()
    val organizationType = viewModel.organizationType.toList()
    val serviceType = viewModel.serviceType

    // ---- local UI state ----
    val options = listOf("Map", "List")

    var closeExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    var selectedSite by remember { mutableStateOf<Site?>(null) }

    var searchBarExpanded =
        searchQuery.length >= 3 && predictions.isNotEmpty() && !closeExpanded

    // ---- launchers / effects ----
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            Log.e("Search Result", "Location permission was denied by the user.")
        }
    }

    LaunchedEffect(Unit) {
        if (viewModel.searchQuery.isBlank()) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    viewModel.fetchUserLocation(context, fusedLocationClient)
                }

                else -> {
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }

    }

    LaunchedEffect(lat, lon, radiusMiles, days, organizationType, serviceType) {
        println("LaunchedEffect run lat lon")
        viewModel.fetchNearbySites(
            lat = lat,
            lon = lon,
            radiusMiles = radiusMiles,
            days = days,
            organizationType = organizationType,
            serviceType = serviceType
        )
    }

    // ---- helpers ----
    fun updateCloseExpanded(closeStatus: Boolean) {
        closeExpanded = closeStatus
    }

    fun clearSelectedSite() {
        selectedSite = null
    }

    fun updateSelectedIndex(index: Int) {
        selectedIndex = index
    }


    SearchTopBar(
        searchBarExpanded = searchBarExpanded,
        placesClient = placesClient,
        updateClosedExpanded = { updateCloseExpanded(it) },
        onClickBack = onClickBack,
        onClickFilterButton = onClickFilterButton,
        searchQuery = searchQuery,
        predictions = predictions,
        fetchPredictions = fetchPredictions,
        onSearchQueryChange = onSearchQueryChange,
        fetchPlaceLatLng = fetchPlaceLatLng,
        resetSearch = resetSearch,
        clearSelectedSite = { clearSelectedSite() },
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp)
    ) {

        SearchResultHeader(
            modifier = modifier,
            numOfFilters = numOfFilters,
            isLoading = isLoading,
            totalResults = totalResults,
            options = options,
            selectedIndex = selectedIndex,
            onSelectedIndexChange = ::updateSelectedIndex
        )

        HorizontalDivider()

        when (options[selectedIndex]) {
            "Map" ->
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ContainedLoadingIndicator(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                        )
                    }

                } else {
                    SiteMapSection(
                        sites = sites,
                        selectedSite = selectedSite,
                        onSiteSelected = { clickedSite ->
                            selectedSite = clickedSite
                        },
                        lat,
                        lon,
                        onClickSiteDetail = { onClickSiteDetail(it) }
                    )
                }

            "List" -> {
                SiteListSection(
                    sites = sites,
                    onclickSiteDetail = { onClickSiteDetail(it) }
                )

            }
        }
    }
}




data class SearchResultUiState(
    val isLoading: Boolean,
    val numOfFilters: Int,
    val totalResults: Int,
    val sites: List<Site>,
    val selectedIndex: Int = 0,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchResultContent(
    state: SearchResultUiState,
    onSelectedIndexChange: (Int) -> Unit,
    onClickSiteDetail: (Int) -> Unit,
) {
    val options = listOf("Map", "List")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp)
            .testTag("search_root")
    ) {
        SearchResultHeader(
            numOfFilters = state.numOfFilters.toString(),
            isLoading = state.isLoading,
            totalResults = state.totalResults,
            options = options,
            selectedIndex = state.selectedIndex,
            onSelectedIndexChange = onSelectedIndexChange
        )

        HorizontalDivider()

        when (options[state.selectedIndex]) {
            "Map" -> {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("results_loading"),
                        contentAlignment = Alignment.Center
                    ) {
                        ContainedLoadingIndicator()
                    }
                } else {
                    Box(Modifier.fillMaxSize().testTag("map_container"))
                }
            }

            "List" -> {
                Box(Modifier.fillMaxSize().testTag("list_container")) {
                    SiteListSection(
                        sites = state.sites,
                        onclickSiteDetail = onClickSiteDetail
                    )
                }
            }
        }
    }
}


