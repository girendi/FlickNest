package com.girendi.flicknest.data.response

import com.girendi.flicknest.data.models.Genre
import com.google.gson.annotations.SerializedName

data class ListGenresResponse (
    @field:SerializedName("genres")
    val listGenre: List<Genre>
)