package com.girendi.flicknest.di

import com.girendi.flicknest.BuildConfig
import com.girendi.flicknest.data.api.ApiService
import com.girendi.flicknest.data.repository.GenreRepositoryImpl
import com.girendi.flicknest.data.repository.MovieByGenreRepositoryImpl
import com.girendi.flicknest.domain.repository.GenreRepository
import com.girendi.flicknest.domain.repository.MovieByGenreRepository
import com.girendi.flicknest.domain.usecase.FetchDetailMovieUseCase
import com.girendi.flicknest.domain.usecase.FetchGenreUseCase
import com.girendi.flicknest.domain.usecase.FetchMovieByGenreUseCase
import com.girendi.flicknest.presentation.detail.DetailMovieViewModel
import com.girendi.flicknest.presentation.genres.GenreViewModel
import com.girendi.flicknest.presentation.movie.ListMovieViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.APP_KEY)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }

    single<GenreRepository> { GenreRepositoryImpl(api = get()) }
    single<MovieByGenreRepository> { MovieByGenreRepositoryImpl(api = get()) }

    factory { FetchGenreUseCase(genreRepository = get()) }
    factory { FetchMovieByGenreUseCase(movieByGenreRepository = get()) }
    factory { FetchDetailMovieUseCase(movieByGenreRepository = get()) }

    viewModel { GenreViewModel(fetchGenreUseCase = get()) }
    viewModel { ListMovieViewModel(fetchMovieByGenreUseCase = get()) }
    viewModel { DetailMovieViewModel(fetchDetailMovieUseCase = get()) }

}