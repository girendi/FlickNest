package com.girendi.flicknest.core.data.response

import com.girendi.flicknest.core.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class ListGenresResponse (
    @field:SerializedName("genres")
    val listGenre: List<Genre>
)