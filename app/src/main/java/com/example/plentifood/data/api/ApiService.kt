package com.example.plentifood.data.api

import com.example.plentifood.data.models.request.AddSiteRequest
import com.example.plentifood.data.models.request.LoginRequest
import com.example.plentifood.data.models.request.RegisterRequest
import com.example.plentifood.data.models.response.LoginResponse
import com.example.plentifood.data.models.response.NearbySitesResponse
import com.example.plentifood.data.models.response.OrganizationResponse
import com.example.plentifood.data.models.response.RegisterResponse
import com.example.plentifood.data.models.response.Site
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("sites/nearby")
    suspend fun getNearbySites(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("radius_miles") radiusMiles: Int,

        //optional filters
        @Query("day") days: List<String>? = null,
        @Query("organization_type") organizationType: List<String>? = null,
        @Query("service") serviceType: List<String>? = null,
        ): NearbySitesResponse

    @GET("sites/{siteId}")
    suspend fun getSite(
        @Path("siteId") siteId: Int
    ): Site

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse


    @GET("organizations/{organizationId}")
    suspend fun getOrganization(
        @Path("organizationId") organizationId: Int
    ): OrganizationResponse

    @Headers("Content-Type: application/json")
    @POST("/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterResponse

    @Headers("Content-Type: application/json")
    @POST("/organizations/{organizationId}/sites")
    suspend fun siteRegister(
        @Path("organizationId") organizationId: Int,
        @Body body: AddSiteRequest
    ): Site

}




