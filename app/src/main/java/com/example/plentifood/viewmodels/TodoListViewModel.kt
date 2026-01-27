package com.example.plentifood.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoListViewModel: ViewModel() {

    private val _todos = MutableStateFlow(
        (1..100).map{"Todo $it"}
    )
    // asStateFlow() turns it into read-only. UI can observe, but cannot modify
    val todos = _todos.asStateFlow()
}