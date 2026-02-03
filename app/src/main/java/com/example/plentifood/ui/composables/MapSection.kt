package com.example.plentifood.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.plentifood.data.models.site.Site
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch
import kotlin.collections.forEach

@Composable
fun MapSection(
    modifier: Modifier,
    sites: List<Site>,
    selectedSite: Site?,
    onSiteSelected: (Site?) -> Unit,
    userLat: Double,
    userLon: Double
) {

    println("LAT: $userLat, LON: $userLon")
    val userLocation = LatLng(userLat, userLon)
    val userMarkerState = rememberUpdatedMarkerState(userLocation)
    val initialTarget = selectedSite?.let { site ->
        LatLng(site.latitude, site.longitude)
    } ?: userLocation

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            initialTarget,
            12f
        )
    }

    val scope = rememberCoroutineScope()

    fun focusOnSite(site: Site) {
        scope.launch {
            val sitePosition = LatLng(site.latitude, site.longitude)
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(sitePosition, 14f, 0f, 0f)
                ),
                1000
            )

        }
    }


    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapClick = { onSiteSelected(null) }
    ) {

        Marker(
            state = userMarkerState,
            title = "Your Current Location",
            snippet = "Marker in Seattle",
            onClick = { true }
        )

        sites.forEach { site ->
            val isSelected = selectedSite?.id == site.id
            val position = LatLng(site.latitude, site.longitude)

            val hue = if (isSelected) {
                BitmapDescriptorFactory.HUE_ORANGE
            } else {
                when (site.organizationType) {
                    "food_bank" -> BitmapDescriptorFactory.HUE_MAGENTA
                    "church" -> BitmapDescriptorFactory.HUE_CYAN
                    "non_profit" -> BitmapDescriptorFactory.HUE_VIOLET
                    else -> BitmapDescriptorFactory.HUE_AZURE
                }
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
                    focusOnSite(site)
                    true
                }
            )
        }
    }
}


