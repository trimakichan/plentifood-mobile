package com.example.plentifood.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.LoginRepository
import com.example.plentifood.data.models.response.LoginResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class LoginViewModel(): ViewModel() {

    private val repository = LoginRepository()

    var loginResponse by mutableStateOf<LoginResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    fun consumeLoginSuccess() {
        loginResponse = null
    }

    fun logout() {
        loginResponse = null
        errorMessage = null
        isLoading = false
    }


    fun login(username: String) {
        isLoading = true
        errorMessage = null
        loginResponse = null

        viewModelScope.launch {
            try {
                val response = repository.login(username)
                loginResponse = response
                println("Login response: $loginResponse")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorMessage = errorBody
                    ?.let { JSONObject(it).optString("error") }
                    ?.takeIf { it.isNotBlank() }
                    ?: "Login failed"
            } catch (e: Exception) {
                errorMessage = e.message
                println("Login Error $errorMessage")
            } finally {
                isLoading = false
            }
        }
    }


}

