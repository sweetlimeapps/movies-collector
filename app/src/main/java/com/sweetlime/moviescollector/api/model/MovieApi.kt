package com.sweetlime.moviescollector.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieApi(
    @Expose @SerializedName("id") var id: Int,
    @Expose @SerializedName("poster_path") var posterPath: String,
    @Expose @SerializedName("original_title") var title: String,
    @Expose @SerializedName("overview") var overview: String
)