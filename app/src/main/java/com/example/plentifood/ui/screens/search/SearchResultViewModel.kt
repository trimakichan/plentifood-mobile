package com.example.plentifood.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.NearbySiteRepository
import com.example.plentifood.data.api.SiteRepository
import com.example.plentifood.data.models.site.Site
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var numOfFilters by mutableIntStateOf(0)
    var lat by mutableDoubleStateOf(47.6204)
    var lon by mutableDoubleStateOf(-122.3494)
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