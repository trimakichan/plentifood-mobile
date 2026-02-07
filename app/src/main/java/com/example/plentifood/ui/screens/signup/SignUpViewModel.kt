package com.example.plentifood.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.RegisterRepository
import com.example.plentifood.data.models.request.AdminRequest
import com.example.plentifood.data.models.request.OrgRequest
import com.example.plentifood.data.models.request.RegisterRequest
import com.example.plentifood.data.models.response.RegisterResponse
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

class SignUpViewModel(): ViewModel() {

    var repository = RegisterRepository()

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var registerResponse by mutableStateOf<RegisterResponse?>(null)
        private set

    fun consumeRegisterSuccess() {
        registerResponse  = null
    }


    fun  buildRegisterRequest(
        name: String,
        organizationType: String,
        WebsiteUrl: String = "",
        username: String
        ):  RegisterRequest  {

        val orgTypeApiValue = when (organizationType) {
            "Food Bank" -> "food_bank"
            "Nonprofit" -> "non_profit"
            "Church" -> "church"
            "Community Center" -> "community_center"
            else -> "others"
        }

        return RegisterRequest(
            organization = OrgRequest(
                name = name,
                websiteUrl = WebsiteUrl,
                organizationType = orgTypeApiValue
            ),
            admin = AdminRequest(
                username = username
            )
        )

    }

    fun register(
        name: String,
        organizationType: String,
        WebsiteUrl: String = "",
        username: String
    ) {

        val requestBody =  buildRegisterRequest(
            name,
            organizationType,
            WebsiteUrl,
            username
        )

        isLoading = true
        errorMessage = null
        registerResponse = null


        viewModelScope.launch {
            try {
                val response = repository.register(requestBody)
                registerResponse = response

                println("Register response: $registerResponse")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorMessage = errorBody
                    ?.let { JSONObject(it).optString("error") }
                    ?.takeIf { it.isNotBlank() }
                    ?: "Register failed"
                println("Register HTTP Error $errorMessage")
            } catch (e: Exception) {
                errorMessage = e.message
                println("Register Error $errorMessage")
            } finally {
                isLoading = false
            }
        }
    }

}