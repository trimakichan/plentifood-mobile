package com.example.plentifood.data.api

import com.example.plentifood.data.models.site.NearbySitesResponse
import com.example.plentifood.data.models.site.Site

class NearbySiteRepository {
    suspend fun getNearbySites(
        lat: Double,
        lon: Double,
        radiusMiles: Int
    ): NearbySitesResponse {
        return RetrofitInstance.api.getNearbySites(
            lat,
            lon,
            radiusMiles
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