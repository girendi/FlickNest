package com.girendi.flicknest.domain.repository

import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Review
import com.girendi.flicknest.data.model.Video
import com.girendi.flicknest.domain.Result

interface MovieByGenreRepository{
    suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<List<Movie>>
    suspend fun fetchMovieDetail(movieId: Int): Result<Movie>
    suspend fun fetchMovieReviews(page: Int, movieId: Int): Result<List<Review>>

    suspend fun fetchMovieVideos(movieId: Int): Result<List<Video>>
}