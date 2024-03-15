package com.girendi.flicknest.core.di

import androidx.room.Room
import com.girendi.flicknest.core.BuildConfig
import com.girendi.flicknest.core.data.source.local.LocalDataSource
import com.girendi.flicknest.core.data.source.local.room.FlickNestDatabase
import com.girendi.flicknest.core.data.source.remote.api.ApiService
import com.girendi.flicknest.core.data.source.remote.repository.FavoriteRepositoryImpl
import com.girendi.flicknest.core.data.source.remote.repository.GenreRepositoryImpl
import com.girendi.flicknest.core.data.source.remote.repository.MovieRepositoryImpl
import com.girendi.flicknest.core.data.source.remote.repository.ReviewRepositoryImpl
import com.girendi.flicknest.core.data.source.remote.repository.VideoRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
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
}

val databaseModule = module {
    factory { get<FlickNestDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FlickNestDatabase::class.java, "FlickNest.db"
        ).fallbackToDestructiveMigration().build()
    }
    single { LocalDataSource(movieDao = get()) }
}

val repositoryModule = module {
    single<com.girendi.flicknest.core.domain.repository.GenreRepository> { GenreRepositoryImpl(apiService = get()) }
    single<com.girendi.flicknest.core.domain.repository.MovieRepository> { MovieRepositoryImpl(
        apiService = get(),
        localDataSource = get()
    ) }
    single<com.girendi.flicknest.core.domain.repository.VideoRepository> { VideoRepositoryImpl(apiService = get()) }
    single<com.girendi.flicknest.core.domain.repository.ReviewRepository> { ReviewRepositoryImpl(apiService = get()) }
    single<com.girendi.flicknest.core.domain.repository.FavoriteRepository> { FavoriteRepositoryImpl(localDataSource = get()) }
}

val factoryModule = module {
    factory { com.girendi.flicknest.core.domain.usecase.FetchGenreUseCase(genreRepository = get()) }
    factory { com.girendi.flicknest.core.domain.usecase.FetchMovieByFilterUseCase(movieRepository = get()) }
    factory {
        com.girendi.flicknest.core.domain.usecase.FetchDetailMovieUseCase(
            movieRepository = get(),
            reviewRepository = get(),
            videoRepository = get()
        )
    }
    factory {
        com.girendi.flicknest.core.domain.usecase.FetchMovieUseCase(
            movieRepository = get(),
            videoRepository = get()
        )
    }
    factory { com.girendi.flicknest.core.domain.usecase.FetchFavoriteUseCase(favoriteRepository = get()) }
}