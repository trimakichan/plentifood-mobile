package com.example.plentifood.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.plentifood.ui.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {
    private  val _uiState = MutableStateFlow(HomeUiState())

    val uiState = _uiState.asStateFlow()
}