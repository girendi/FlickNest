package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.source.remote.response.ListGenresResponse
import com.girendi.flicknest.core.data.Result

interface GenreRepository {
    suspend fun fetchGenreMovie(): Result<ListGenresResponse>
}