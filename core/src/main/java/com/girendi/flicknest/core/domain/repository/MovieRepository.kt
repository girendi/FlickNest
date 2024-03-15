package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.source.remote.response.ListMovieResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.data.source.remote.response.MovieResponse
import com.girendi.flicknest.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository{
    suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<ListMovieResponse>
    suspend fun fetchMovieDetail(movieId: Int): Result<MovieResponse>
    suspend fun fetchPopularMovies(page: Int): Result<ListMovieResponse>
    suspend fun fetchTrendingMovies(page: Int): Result<ListMovieResponse>
    suspend fun insertFavoriteMovie(movie: Movie)
    fun getFavoriteMovieById(movieId: Int): Flow<Movie?>
    suspend fun deleteFavoriteMovie(movie: Movie)
}