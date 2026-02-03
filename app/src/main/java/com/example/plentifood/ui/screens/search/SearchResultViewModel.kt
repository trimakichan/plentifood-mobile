package com.example.plentifood.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.NearbySiteRepository
import com.example.plentifood.data.models.site.Site
import com.example.plentifood.ui.utils.ManifestUtils
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

    var lat by mutableDoubleStateOf(47.6204)
        private set
    var lon by mutableDoubleStateOf(-122.3494)
        private set

    var radiusMiles by mutableIntStateOf(5)
        private set

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

    fun updateCoordinates(newLat: Double, newLon: Double) {
       lat = newLat
       lon = newLon
    }

    fun onUpdateNumOfFilters(numOfFilters: Int) {
        this.numOfFilters = numOfFilters
    }

    fun onChangeRadius(radius: Int) {
        radiusMiles = radius
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


}