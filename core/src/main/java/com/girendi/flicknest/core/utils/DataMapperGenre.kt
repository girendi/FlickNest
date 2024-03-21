package com.girendi.flicknest.core.utils

import com.girendi.flicknest.core.data.source.remote.response.GenreResponse
import com.girendi.flicknest.core.domain.model.Genre

object DataMapperGenre {
    fun mapResponsesToDomain(input: List<GenreResponse>): List<Genre> {
        val genreList = ArrayList<Genre>()
        input.map {
            val genre = Genre(
                id = it.id,
                name = it.name
            )
            genreList.add(genre)
        }
        return genreList
    }
}