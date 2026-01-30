package com.example.plentifood.ui.screens.search

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.plentifood.data.models.site.Site
import com.example.plentifood.ui.composables.InfoCard
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton
import com.example.plentifood.ui.theme.PlentifoodTheme
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
//    ViewModel() will keep the previous changes even after configuration gets updated.
    viewModel: SearchResultViewModel = viewModel(),
    onClickSiteDetail: (Int) -> Unit
) {
    val query = viewModel.query
    val options = listOf("Map", "List")
//    use rememberSaveable to keep the values during configuration changes.
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val isLoading = viewModel.isLoading
    val totalResults = viewModel.totalResults
    val sites = viewModel.sites

    var selectedSite by remember { mutableStateOf<Site?>(null)}

    LaunchedEffect(Unit) {
        viewModel.fetchNearbySites(47.6204, -122.3494, 5)
    }

    LaunchedEffect(viewModel.sites) {
        println("Results: $totalResults")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { newText ->
                        viewModel.onQueryChange(newText)
                    },
                    modifier = modifier
                        .weight(1f),
                    placeholder = { Text("search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(34.dp)
                            .rotate(90f)
                    )
                }
            }

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
                                append("3")
                            }

                            append(" Filters")
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )


                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(6.dp)
                            .size(18.dp)
                            .rotate(90f)

                    )

                }


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

        HorizontalDivider()

        when (options[selectedIndex]) {
            "Map" ->

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }

                } else {
                    Box {
                        MapScreen(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            sites = sites,
                            selectedSite = selectedSite,
                            onSiteSelected = { clickedSite ->
                                selectedSite = clickedSite
                            }
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

@Composable
fun MapScreen(
    modifier: Modifier,
    sites: List<Site>,
    selectedSite: Site?,
    onSiteSelected: (Site?) -> Unit
)
{
    val userLocation = LatLng(47.6062, -122.3321)
    val userMarkerState = rememberUpdatedMarkerState(userLocation)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 14f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapClick = { onSiteSelected(null)}
    ) {

        Marker(
            state = userMarkerState,
            title = "Your Current Location",
            snippet = "Marker in Seattle",
            onClick = { true }
        )

        sites.forEach { site ->
            val position = LatLng(site.latitude, site.longitude)

            val hue = when (site.organizationType) {
                "food_bank" -> BitmapDescriptorFactory.HUE_ORANGE
                "church" -> BitmapDescriptorFactory.HUE_ROSE
                "non_profit" -> BitmapDescriptorFactory.HUE_VIOLET
                else -> BitmapDescriptorFactory.HUE_AZURE
            }
            Marker(
                state = MarkerState(position),
                title = site.name,
                snippet = site.city,
                icon = BitmapDescriptorFactory.defaultMarker(
                    hue
                ),
                onClick = {
                            onSiteSelected(site)
                            true
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview() {
    PlentifoodTheme {
        SearchResultScreen(
            onClickSiteDetail = {}
        )
    }
}

