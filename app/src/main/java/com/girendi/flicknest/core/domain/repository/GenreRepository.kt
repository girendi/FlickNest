package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.response.ListGenresResponse
import com.girendi.flicknest.core.domain.Result

interface GenreRepository {
    suspend fun fetchGenreMovie(): Result<ListGenresResponse>
}