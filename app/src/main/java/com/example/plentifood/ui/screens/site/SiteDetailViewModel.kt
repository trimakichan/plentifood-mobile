package com.example.plentifood.ui.screens.site

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.data.api.SiteRepository
import com.example.plentifood.data.models.site.Site
import kotlinx.coroutines.launch

class SiteDetailViewModel: ViewModel() {

    var site by mutableStateOf<Site?>(null)
        private set

    private val repository = SiteRepository()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchSite(siteId: Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = repository.getSite(siteId)
                site = response

                println("サイト: ${site?.name}")

            } catch (e: Exception) {
                errorMessage = e.message
                println("ERROR $errorMessage")
            } finally {
                isLoading = false
            }
        }
    }

}