package com.example.plentifood.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton
import com.example.plentifood.ui.screens.Home
import com.example.plentifood.ui.theme.PlentifoodTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState


@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
//    ViewModel() will keep the previous changes even after configuration gets updated.
    viewModel: SearchResultScreenViewMode = viewModel()
) {
    val query = viewModel.query.value

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
                    viewModel.onQueryChange(newText) },
                modifier = modifier.weight(1f),
                placeholder = { Text("search")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
            }) {
                Icon(
                    imageVector = Icons.Filled.Tune,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(28.dp)
                        .rotate(90f)

                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SingleChoiceSegmentedButton()

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Text(
                "3 Filters",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
                )

            Text(
                "24 Results",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

        }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SimpleMapScreen(  modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        )


    }

}

@Composable
fun SimpleMapScreen(modifier: Modifier) {
    val seattle = LatLng(47.6062, -122.3321)
    val seattleMarkerState = rememberUpdatedMarkerState(position = seattle)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seattle, 14f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = seattleMarkerState,
            title = "Seattle",
            snippet = "Marker in Seattle"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview() {
    PlentifoodTheme {
        SearchResultScreen()
    }
}

