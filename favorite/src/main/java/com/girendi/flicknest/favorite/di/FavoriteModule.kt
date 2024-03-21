package com.girendi.flicknest.favorite.di

import com.girendi.flicknest.favorite.presentation.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(fetchFavoriteUseCase = get()) }
}