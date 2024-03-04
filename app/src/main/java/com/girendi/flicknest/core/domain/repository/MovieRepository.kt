package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.response.ListMovieResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.Result

interface MovieRepository{
    suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<ListMovieResponse>
    suspend fun fetchMovieDetail(movieId: Int): Result<Movie>
    suspend fun fetchPopularMovies(page: Int): Result<ListMovieResponse>
    suspend fun fetchTrendingMovies(page: Int): Result<ListMovieResponse>
}