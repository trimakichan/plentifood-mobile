package com.example.plentifood.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchBar(
    searchQuery: String,
    predictions: List<AutocompletePrediction>,
    fetchPredictions: (String, PlacesClient) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSuggestionClick: (AutocompletePrediction) -> Unit,
    placesClient: PlacesClient,
    expanded: Boolean = false,
    updateClosedExpanded: (Boolean) -> Unit = {},
    resetSearch: () -> Unit,
    clearSelectedSite: () -> Unit,
    modifier: Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    DockedSearchBar(
        inputField = {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
            ) {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = {
                        clearSelectedSite()

                        updateClosedExpanded(false)
                        onSearchQueryChange(it)
                        fetchPredictions(it, placesClient)
                    },
                    onSearch = {},
                    expanded = expanded,
                    onExpandedChange = {},
                    placeholder = {
                        Text(
                            "Search location",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    clearSelectedSite()
                                    resetSearch()
                                }
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    }
                )
            }
        },

        expanded = expanded,
        onExpandedChange = {},

        modifier = modifier
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val cornerRadius = 28.dp.toPx()
                drawRoundRect(
                    color = colorScheme.secondary,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius),
                    style = Stroke(width = strokeWidth)
                )
            },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        content = {
            LazyColumn() {
                items(predictions) { prediction ->
                    ListItem(
                        headlineContent = { Text(prediction.getPrimaryText(null).toString()) },
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onSuggestionClick(prediction)
                                updateClosedExpanded(true)
                            }
                    )
                }
            }
        }
    )
}

