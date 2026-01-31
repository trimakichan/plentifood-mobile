package com.example.plentifood.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.NearbySiteRepository
import com.example.plentifood.data.api.SiteRepository
import com.example.plentifood.data.models.site.Site
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {
    var query by mutableStateOf("")
        private set

    var numOfFilters by mutableIntStateOf(0)

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
        query = newValue
    }

    fun fetchNearbySites(lat: Double, lon: Double, radiusMiles: Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = repository.getNearbySites(lat, lon, radiusMiles)

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