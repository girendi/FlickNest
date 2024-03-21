package com.girendi.flicknest.core.utils

import com.girendi.flicknest.core.data.source.local.entity.MovieEntity
import com.girendi.flicknest.core.data.source.remote.response.MovieResponse
import com.girendi.flicknest.core.domain.model.Movie

object DataMapperMovie {
    fun mapResponsesToDomain(input: List<MovieResponse>): List<Movie> {
        val movieList = ArrayList<Movie>()
        input.map {
            val movie = mapResponseToDomain(it)
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntityToDomain(input: List<MovieEntity>): List<Movie> {
        val movieList = ArrayList<Movie>()
        input.map {
            val movie = mapEntityToDomain(it)
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseToDomain(input: MovieResponse): Movie =
        Movie(
            id = input.id,
            title = input.title,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            overview = input.overview,
            voteAverage = input.voteAverage,
            homepage = input.homepage,
            tagline = input.tagline,
            backdropPath = input.backdropPath
        )

    fun mapDomainToEntity(input: Movie): MovieEntity {
        return MovieEntity(
            id = input.id,
            title = input.title,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage
        )
    }

    fun mapEntityToDomain(input: MovieEntity): Movie {
        return Movie(
            id = input.id,
            title = input.title,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage
        )
    }
}