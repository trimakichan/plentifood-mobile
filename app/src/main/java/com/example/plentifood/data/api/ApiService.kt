package com.example.plentifood.data.api

import com.example.plentifood.data.models.site.NearbySitesResponse
import com.example.plentifood.data.models.site.Site
import retrofit2.http.GET
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

}