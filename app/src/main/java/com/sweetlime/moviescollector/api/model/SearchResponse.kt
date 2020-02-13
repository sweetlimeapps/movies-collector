package com.sweetlime.moviescollector.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @Expose @SerializedName("page") var page: Int,
    @Expose @SerializedName("total_pages") var totalPages: Int,
    @Expose @SerializedName("total_results") var totalResults: Int,
    @Expose @SerializedName("results") var results: List<SearchItem>
)