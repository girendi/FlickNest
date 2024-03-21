package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("site")
    val site: String,
    @field:SerializedName("type")
    val type: String
)
