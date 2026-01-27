package com.example.plentifood.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoDetailViewModel(
    private val todo: String
): ViewModel() {

    private val _state = MutableStateFlow(TodoDetailState(todo))
    // asStateFlow() turns it into read-only. UI can observe, but cannot modify
    val state = _state.asStateFlow()
}

data class TodoDetailState(
    val todo: String
)