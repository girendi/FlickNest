package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class ListVideoResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("results")
    val listVideo: List<VideoResponse>
)