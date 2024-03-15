package com.girendi.flicknest.core.utils

import com.girendi.flicknest.core.data.source.remote.response.VideoResponse
import com.girendi.flicknest.core.domain.model.Video

object DataMapperVideo {
    fun mapResponsesToDomain(input: List<VideoResponse>): List<Video> {
        val videoList = ArrayList<Video>()
        input.map {
            val video = Video(
                id = it.id,
                key = it.key,
                name = it.name,
                site = it.site,
                type = it.type
            )
            videoList.add(video)
        }
        return videoList
    }
}