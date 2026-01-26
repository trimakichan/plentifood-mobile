package com.example.plentifood.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchResultScreenViewMode: ViewModel() {
    var query = mutableStateOf("")
        private set

    fun onQueryChange(newValue: String) {
        query.value = newValue
    }
}