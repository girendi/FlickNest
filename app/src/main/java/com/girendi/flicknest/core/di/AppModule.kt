package com.girendi.flicknest.core.di

import com.girendi.flicknest.BuildConfig
import com.girendi.flicknest.core.data.api.ApiService
import com.girendi.flicknest.core.data.repository.GenreRepositoryImpl
import com.girendi.flicknest.core.data.repository.MovieRepositoryImpl
import com.girendi.flicknest.core.data.repository.ReviewRepositoryImpl
import com.girendi.flicknest.core.data.repository.VideoRepositoryImpl
import com.girendi.flicknest.core.domain.repository.GenreRepository
import com.girendi.flicknest.core.domain.repository.MovieRepository
import com.girendi.flicknest.core.domain.repository.ReviewRepository
import com.girendi.flicknest.core.domain.repository.VideoRepository
import com.girendi.flicknest.core.domain.usecase.FetchDetailMovieUseCase
import com.girendi.flicknest.core.domain.usecase.FetchGenreUseCase
import com.girendi.flicknest.core.domain.usecase.FetchMovieByFilterUseCase
import com.girendi.flicknest.core.domain.usecase.FetchMovieUseCase
import com.girendi.flicknest.presentation.detail.DetailMovieViewModel
import com.girendi.flicknest.presentation.genres.GenreViewModel
import com.girendi.flicknest.presentation.home.HomeViewModel
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

    single<GenreRepository> { GenreRepositoryImpl(apiService = get()) }
    single<MovieRepository> { MovieRepositoryImpl(api = get()) }
    single<VideoRepository> { VideoRepositoryImpl(apiService = get()) }
    single<ReviewRepository> { ReviewRepositoryImpl(apiService = get()) }

    factory { FetchGenreUseCase(genreRepository = get()) }
    factory { FetchMovieByFilterUseCase(movieRepository = get()) }
    factory { FetchDetailMovieUseCase(
        movieRepository = get(),
        reviewRepository = get(),
        videoRepository = get()
    )}
    factory { FetchMovieUseCase(
        movieRepository = get(),
        videoRepository = get()
    )}

    viewModel { GenreViewModel(fetchGenreUseCase = get()) }
    viewModel { ListMovieViewModel(fetchMovieByFilterUseCase = get()) }
    viewModel { DetailMovieViewModel(fetchDetailMovieUseCase = get()) }
    viewModel { HomeViewModel(fetchMovieUseCase = get()) }

}