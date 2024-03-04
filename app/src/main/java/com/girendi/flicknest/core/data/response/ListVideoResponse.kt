package com.girendi.flicknest.core.data.response

import com.girendi.flicknest.core.domain.model.Video
import com.google.gson.annotations.SerializedName

class ListVideoResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("results")
    val listVideo: List<Video>
)