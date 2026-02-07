package com.example.plentifood.data.api

import com.example.plentifood.data.models.request.AddSiteRequest
import com.example.plentifood.data.models.request.RegisterRequest
import com.example.plentifood.data.models.request.LoginRequest
import com.example.plentifood.data.models.response.LoginResponse
import com.example.plentifood.data.models.response.NearbySitesResponse
import com.example.plentifood.data.models.response.OrganizationResponse
import com.example.plentifood.data.models.response.RegisterResponse
import com.example.plentifood.data.models.response.Site


class NearbySiteRepository {
    suspend fun getNearbySites(
        lat: Double,
        lon: Double,
        radiusMiles: Int,
        day: List<String>? = null,
        organizationType: List<String>? = null,
        service: List<String>? = null,
    ): NearbySitesResponse {
        println("FETCH with days=$day orgTypes=$organizationType service=$service")

        return RetrofitInstance.api.getNearbySites(
            lat,
            lon,
            radiusMiles,
            day,
            organizationType,
            service
        )
    }
}

class SiteRepository {
    suspend fun getSite(
        siteId: Int
    ): Site {
        return RetrofitInstance.api.getSite(
            siteId
        )
    }
}
class LoginRepository {
    suspend fun login(
        username: String,
    ): LoginResponse {
        return RetrofitInstance.api.login(
            LoginRequest(username)
        )
    }

}

class AdminDashboardRepository {
    suspend fun adminDashboard(
        organizationId: Int
    ): OrganizationResponse {
        return RetrofitInstance.api.getOrganization(
            organizationId
        )
    }

}

class RegisterRepository {
    suspend fun register(
        requestBody: RegisterRequest
    ): RegisterResponse {
        return RetrofitInstance.api.register(
            requestBody
        )
    }
}

class SiteRegisterRepository {
    suspend fun addSite(
        organizationId: Int,
        requestBody: AddSiteRequest
    ): Site {
        return RetrofitInstance.api.siteRegister(
            organizationId,
            requestBody
        )
    }
}