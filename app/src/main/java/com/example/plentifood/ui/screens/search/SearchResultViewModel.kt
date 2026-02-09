package com.example.plentifood.ui.screens.search

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.NearbySiteRepository
import com.example.plentifood.data.models.response.Site
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.launch

class SearchResultViewModel(
) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var predictions by mutableStateOf<List<AutocompletePrediction>>(emptyList())
        private set

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    var numOfFilters by mutableIntStateOf(0)

    var defaultLat = 47.6204
    var defaultLon = -122.3494

    var lat by mutableDoubleStateOf(defaultLat)
        private set
    var lon by mutableDoubleStateOf(defaultLon)
        private set
    var radiusMiles by mutableIntStateOf(5)


    var days = mutableStateListOf<String>()
        private set

    var organizationType = mutableStateListOf<String>()
        private set

    var serviceType = mutableStateListOf<String>()
        private set

    private val repository = NearbySiteRepository()

    var sites by mutableStateOf<List<Site>>(emptyList())
        private set
    var totalResults by mutableStateOf(0)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onQueryChange(newValue: String) {
        searchQuery = newValue
    }

    fun resetSearch() {
        lat = defaultLat
        lon = defaultLon
        predictions = emptyList()
        searchQuery = ""
    }

    fun updateCoordinates(newLat: Double, newLon: Double) {
        lat = newLat
        lon = newLon
    }

    fun onUpdateNumOfFilters(numOfFilters: Int) {
        this.numOfFilters = numOfFilters
    }

    fun onUpdateRadius(text: String) {
        if (text.isEmpty()) return

        radiusMiles = text.toInt()
    }

    fun onDaySelected(days: List<String>) {
        this.days.clear()
        this.days.addAll(days)
        println("onDaySelected: ${this.days}")
    }

    fun onOrganizationTypeSelected(orgType: List<String>) {
        this.organizationType.clear()
        this.organizationType.addAll(orgType)
    }

    fun onServiceSelected(serviceType: List<String>) {
        this.serviceType.clear()
        this.serviceType.addAll(serviceType)

    }

    fun fetchPredictions(query: String, client: PlacesClient) {
        // consider debounce
        if (query.length < 3) {
            predictions = emptyList()
            return
        }

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setCountries(listOf("US"))
            .build()

        client.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                predictions = response.autocompletePredictions
            }
            .addOnFailureListener {
                println("Error: ${it.message}")
            }
    }

    fun fetchPlaceLatLng(placeId: String, placesClient: PlacesClient) {
        val request = FetchPlaceRequest.newInstance(placeId, listOf(Place.Field.LOCATION))

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val loc = response.place.location
                if (loc != null) {
                    updateCoordinates(loc.latitude, loc.longitude)
                }
            }
            .addOnFailureListener { e ->
                errorMessage = e.message
                println("fetchPlaceLatLng error: ${e.message}")
            }
    }


    fun fetchNearbySites(
        lat: Double,
        lon: Double,
        radiusMiles: Int,
        days: List<String>? = null,
        organizationType: List<String>? = null,
        serviceType: List<String>? = null,
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = repository.getNearbySites(
                    lat,
                    lon,
                    radiusMiles,
                    days,
                    organizationType,
                    serviceType
                )

                sites = response.results
                totalResults = response.totalResults

                println("Fetched sites: $sites")
            } catch (e: Exception) {
                errorMessage = e.message
                println("ERROR $errorMessage")
            } finally {
                isLoading = false
            }

        }
    }


    // Function to fetch the user's location and update the state
    fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        // Check if the location permission is granted
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                // Fetch the last known location
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        // Update the user's location in the state
                        defaultLat = it.latitude
                        defaultLon = it.longitude
                        val userLatLng = LatLng(it.latitude, it.longitude)

                        updateCoordinates(userLatLng.latitude, userLatLng.longitude)

                    }
                }
            } catch (e: SecurityException) {
                Log.e(
                    "SearchResult",
                    "Permission for location access was revoked: ${e.localizedMessage}"
                )
            }
        } else {
            Log.e("SearchResult", "Location permission is not granted.")
        }
    }

}


