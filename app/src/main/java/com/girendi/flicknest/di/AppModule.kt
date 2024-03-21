package com.girendi.flicknest.di

import com.girendi.flicknest.presentation.detail.DetailMovieViewModel
import com.girendi.flicknest.presentation.genres.GenreViewModel
import com.girendi.flicknest.presentation.home.HomeViewModel
import com.girendi.flicknest.presentation.movie.ListMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GenreViewModel(fetchGenreUseCase = get()) }
    viewModel { ListMovieViewModel(fetchMovieByFilterUseCase = get()) }
    viewModel { DetailMovieViewModel(fetchDetailMovieUseCase = get()) }
    viewModel { HomeViewModel(fetchMovieUseCase = get()) }
}