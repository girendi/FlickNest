package com.girendi.flicknest.domain.repository

import com.girendi.flicknest.data.model.Genre
import com.girendi.flicknest.domain.Result

interface GenreRepository {
    suspend fun fetchGenres(): Result<List<Genre>>
}