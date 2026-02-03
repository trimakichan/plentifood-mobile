package com.example.plentifood.data.api

import com.example.plentifood.data.models.site.LatLng
import com.example.plentifood.data.models.site.NearbySitesResponse
import com.example.plentifood.data.models.site.Site

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
