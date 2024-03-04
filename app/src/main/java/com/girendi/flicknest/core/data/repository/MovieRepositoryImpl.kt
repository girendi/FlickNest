package com.girendi.flicknest.core.data.repository

import com.girendi.flicknest.core.data.api.ApiService
import com.girendi.flicknest.core.data.response.ListMovieResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.MovieRepository

class MovieRepositoryImpl(private val api: ApiService): BaseRepository(), MovieRepository {
    override suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<ListMovieResponse> =
        apiRequest { api.fetchMoviesByGenre(page, genreId) }
    override suspend fun fetchMovieDetail(movieId: Int): Result<Movie> =
        apiRequest { api.fetchMovieDetail(movieId) }
    override suspend fun fetchPopularMovies(page: Int): Result<ListMovieResponse> =
        apiRequest { api.fetchPopularMovie(page) }
    override suspend fun fetchTrendingMovies(page: Int): Result<ListMovieResponse> =
        apiRequest { api.fetchTrendingMovie(page) }
}