package com.example.plentifood.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.plentifood.ui.composables.ButtonWithIcon
import com.example.plentifood.ui.composables.LocationSearchBar
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient


@Composable
fun SearchTopBar(
    searchBarExpanded: Boolean,
    placesClient: PlacesClient?,
    updateClosedExpanded: (Boolean) -> Unit,
    onClickBack: () -> Unit,
    onClickFilterButton: () -> Unit,
    searchQuery: String,
    predictions: List<AutocompletePrediction>,
    fetchPredictions: (String, PlacesClient) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    fetchPlaceLatLng: (String, PlacesClient) -> Unit,
    resetSearch: () -> Unit,
    clearSelectedSite: () -> Unit,

    ) {
    Box(
        modifier = Modifier
            .padding(18.dp)
            .zIndex(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (!searchBarExpanded) {
                ButtonWithIcon(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(42.dp),
                    icon = Icons.Outlined.ArrowBackIosNew,
                    onClick =  onClickBack,
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
                        clearSelectedSite()
                        onSearchQueryChange(prediction.getPrimaryText(null).toString())
                        prediction.placeId.let { placeId ->
                            placesClient.let { client ->
                                fetchPlaceLatLng(
                                    placeId,
                                    client
                                )
                            }
                        }
                    },
                    placesClient,
                    expanded = searchBarExpanded,
                    updateClosedExpanded = { updateClosedExpanded(it) },
                    resetSearch = resetSearch,
                    clearSelectedSite,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (!searchBarExpanded) {
                ButtonWithIcon(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(42.dp)
                        .rotate(90f),
                    icon = Icons.Outlined.Tune,
                    onClick = onClickFilterButton,
                    description = "Filter Icon"
                )
            }
        }

    }
}