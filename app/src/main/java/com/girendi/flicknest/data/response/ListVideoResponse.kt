package com.girendi.flicknest.data.response

import com.girendi.flicknest.data.models.Video
import com.google.gson.annotations.SerializedName

class ListVideoResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("results")
    val listVideo: List<Video>
)