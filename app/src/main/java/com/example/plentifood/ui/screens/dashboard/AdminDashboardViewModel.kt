package com.example.plentifood.ui.screens.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plentifood.data.api.AdminDashboardRepository
import com.example.plentifood.data.api.SiteRegisterRepository
import com.example.plentifood.data.models.request.AddSiteRequest
import com.example.plentifood.data.models.request.DayHoursRequest
import com.example.plentifood.data.models.response.OrganizationResponse
import com.example.plentifood.ui.state.AddSiteUiState
import com.example.plentifood.ui.state.DayHoursValue
import com.example.plentifood.ui.utils.toApi
import kotlinx.coroutines.launch
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
class AdminDashboardViewModel() : ViewModel() {
    val adminRepository = AdminDashboardRepository()

    val siteRegisterRepository = SiteRegisterRepository()
    var organizationResponse by mutableStateOf<OrganizationResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isSubmitLoading by mutableStateOf(false)
        private set

    var submitSuccess by mutableStateOf(false)

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var uiState by mutableStateOf(AddSiteUiState())
        private set

    val serviceTypes = listOf("Food Bank", "Meal")

    val serviceTypesChecked = mutableStateListOf(false, false)

    fun updateSiteName(value: String) {
        uiState = uiState.copy(siteName = value)
    }

    fun updateAddress1(value: String) {
        uiState = uiState.copy(address1 = value)
    }

    fun updateAddress2(value: String?) {
        if (value == null) return
        uiState = uiState.copy(address2 = value)
    }

    fun updateCity(value: String) {
        uiState = uiState.copy(city = value)
    }

    fun updateState(value: String) {
        uiState = uiState.copy(state = value)
    }

    fun updatePostalCode(value: String) {
        uiState = uiState.copy(postalCode = value)
    }

    fun updatePhone(value: String) {
        uiState = uiState.copy(phone = value)
    }

    fun updateNotes(value: String?) {
        if (value == null) return
        uiState = uiState.copy(notes = value)
    }

    private val eligibilityMap = mapOf(
        "General Public" to "general_public",
        "Older Adults and Eligible" to "older_adults_and_eligible",
        "Youth Young Adults" to "youth_young_adults"
    )

    fun updateEligibility(value: String) {
        eligibilityMap[value]?.let {eligibility ->
            uiState = uiState.copy(
                eligibility = eligibility
            )
        }

    }

    fun getCheckedServiceTypes(): List<String> {
        val checkedServiceTypes = mutableListOf<String>()
        for (i in serviceTypes.indices) {
            if (serviceTypesChecked[i]) {
                checkedServiceTypes.add(serviceTypes[i])
            }
        }
        return checkedServiceTypes
    }

    private val serviceTypesMap = mapOf(
        "Food Bank" to "food_bank",
        "Meal" to "meal"
    )

    fun updateServiceTypes() {
        val selectedTypes = getCheckedServiceTypes()

        val serviceTypes = mutableListOf<String>()
        for (item in selectedTypes) {
            serviceTypesMap[item]?.let { value ->
                serviceTypes.add(value)
            }
        }

        uiState = uiState.copy(
            serviceTypes = serviceTypes
        )
    }

    fun updateOpen(day: String, newOpen: LocalTime) {
        val current = uiState.hoursByDay[day] ?: DayHoursValue(open = null, close = null)
        uiState =
            uiState.copy(hoursByDay = uiState.hoursByDay + (day to current.copy(open = newOpen)))
        println("Hours by day open: ${uiState.hoursByDay}")
    }


    fun updateClose(day: String, newClose: LocalTime) {
        val current = uiState.hoursByDay[day] ?: DayHoursValue(open = null, close = null)
        uiState =
            uiState.copy(hoursByDay = uiState.hoursByDay + (day to current.copy(close = newClose)))
        println("Hours by day close: ${uiState.hoursByDay}")
    }


    fun submitAddSite(organizationId: Int) {
        val hoursRequest: Map<String, List<DayHoursRequest>> =
            uiState.hoursByDay.mapKeys { (day, _) ->
                day.lowercase()
            }.mapValues { (_, value) ->
                if (value.open != null && value.close != null) {
                    listOf(
                        DayHoursRequest(
                            open = value.open.toApi(),
                            close = value.close.toApi()
                        )
                    )
                } else {
                    emptyList()

                }
            }

        viewModelScope.launch {
            try {
                isSubmitLoading = true

                val request = AddSiteRequest(
                    name = uiState.siteName,
                    addressLine1 = uiState.address1,
                    addressLine2 = uiState.address2,
                    city = uiState.city,
                    state = uiState.state,
                    postalCode = uiState.postalCode,
                    phone = uiState.phone,
                    eligibility = uiState.eligibility,
                    serviceNotes = uiState.notes,
                    services = uiState.serviceTypes,
                    hours = hoursRequest,
                )

                val response = siteRegisterRepository.addSite(organizationId, request)
                println("Site Register response: $response")
                submitSuccess = true

            } catch (e: Exception) {
                errorMessage = e.message
                println("Site Submit Error $errorMessage")
                submitSuccess = false

            } finally {
                isSubmitLoading = false
            }
        }
    }

    fun fetchOrganization(orgId: Int) {

        viewModelScope.launch {
            try {
                isLoading = true
                val response = adminRepository.adminDashboard(orgId)
                organizationResponse = response
                println("Organization response: $organizationResponse")

            } catch (e: Exception) {
                errorMessage = e.message
                println("Login Error $errorMessage")

            } finally {
                isLoading = false
            }

        }

    }

}


