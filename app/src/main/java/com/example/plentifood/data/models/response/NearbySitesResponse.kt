package com.example.plentifood.data.models.response

import com.google.gson.annotations.SerializedName

class NearbySitesResponse (
    val results: List<Site>,
    @SerializedName("total_results")
    val totalResults: Int
)