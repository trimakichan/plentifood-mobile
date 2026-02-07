package com.example.plentifood.ui.screens.search


import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import com.example.plentifood.data.models.response.Site
import com.example.plentifood.ui.composables.InfoCard
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.LocationSearchBar
import com.example.plentifood.ui.composables.MapSection
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
//    ViewModel() will keep the previous changes even after configuration gets updated.
    viewModel: SearchResultViewModel = viewModel(),
    onClickSiteDetail: (Int) -> Unit,
    onClickFilterButton: () -> Unit,
    onClickBack: () -> Unit
) {

    val searchQuery = viewModel.searchQuery
    val predictions = viewModel.predictions
    val onSearchQueryChange = viewModel::onSearchQueryChange
    val fetchPredictions = viewModel::fetchPredictions
    val updateCoordinates = viewModel::updateCoordinates
    val context = LocalContext.current
    val placesClient: PlacesClient? = remember {
        val isPreview = false
        if (isPreview) null else Places.createClient(context)
    }
    var closeExpanded by rememberSaveable { mutableStateOf(false) }
    var searchBarExpanded = searchQuery.length >= 3 && predictions.isNotEmpty() && !closeExpanded

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Handle permission requests for accessing fine location
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Fetch the user's location and update the camera if permission is granted
            viewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            // Handle the case when permission is denied
            Log.e("Search Result", "Location permission was denied by the user.")
        }
    }

// Request the location permission when the composable is launched
    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            // Check if the location permission is already granted
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                // Fetch the user's location and update the camera
                viewModel.fetchUserLocation(context, fusedLocationClient)
            }

            else -> {
                // Request the location permission if it has not been granted
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    val options = listOf("Map", "List")
//    use rememberSaveable to keep the values during configuration changes.
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val isLoading = viewModel.isLoading
    val totalResults = viewModel.totalResults
    val sites = viewModel.sites
    var selectedSite by remember { mutableStateOf<Site?>(null) }

    val numOfFilters = viewModel.numOfFilters.toString()

    val lat = viewModel.lat
    val lon = viewModel.lon
    val radiusMiles = viewModel.radiusMiles
    val days = viewModel.days.toList()
    val organizationType = viewModel.organizationType.toList()
    val serviceType = viewModel.serviceType

    LaunchedEffect(lat, lon, radiusMiles, days, organizationType, serviceType) {
        viewModel.fetchNearbySites(
            lat = lat,
            lon = lon,
            radiusMiles = radiusMiles,
            days = days,
            organizationType = organizationType,
            serviceType = serviceType
        )
    }


    Box(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
            .zIndex(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (!searchBarExpanded) {
                ButtonWithIcon(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(34.dp),
                    Icons.Outlined.ArrowBackIosNew,
                    onClickBack,
                    description = "Go Back Icon"
                )
            }


            if (placesClient != null) {
                LocationSearchBar(
                    searchQuery,
                    predictions,
                    fetchPredictions,
                    onSearchQueryChange,
                    onSuggestionClick = { prediction ->
                        onSearchQueryChange(prediction.getPrimaryText(null).toString())
                        prediction.placeId.let { placeId ->
                            placesClient.let { client ->
                                viewModel.fetchPlaceLatLng(
                                    placeId,
                                    client
                                )  // <-- new (Places SDK)
                            }
                        }
                    },
                    placesClient,
                    expanded = searchBarExpanded,
                    updateClosedExpanded = { closeExpanded = it },
                    updateCoordinates,
                    modifier = Modifier
                        .weight(1f)
                )


            }

            Spacer(modifier = Modifier.width(12.dp))

            if (!searchBarExpanded) {
                ButtonWithIcon(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(34.dp)
                        .rotate(90f),
                    Icons.Outlined.Tune,
                    onClickFilterButton,
                    description = "Filter Icon"
                )
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp)
    ) {

        Column(
            modifier = modifier
                .padding(18.dp)
        ) {

            Spacer(modifier = Modifier.height(18.dp))

            SingleChoiceSegmentedButton(
                options,
                selectedIndex,
                onSelectedIndexChange = { selectedIndex = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(numOfFilters)
                            }

                            append(" Filters")
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )


                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "Cancel Icon",
                        modifier = Modifier
                            .padding(6.dp)
                            .size(18.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )

                }

                if (isLoading) {

                    Text(
                        "Loading...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(totalResults.toString())
                            }

                            append(" Results")
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )

                }

            }
        }

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
                    Box {
                        MapSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            sites = sites,
                            selectedSite = selectedSite,
                            onSiteSelected = { clickedSite ->
                                selectedSite = clickedSite
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

            "List" -> {
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
                            onClickSiteDetail = { onClickSiteDetail(site.id) }
                        )
                    }
                }
            }
        }
    }


}


//@Preview(showBackground = true)
//@Composable
//fun SearchResultScreenPreview() {
//    PlentifoodTheme {
//        SearchResultScreen(
//            onClickSiteDetail = {},
//            onClickFilterButton = {}
//        )
//    }
//}

