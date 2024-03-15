package com.girendi.flicknest.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.usecase.FetchFavoriteUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class FavoriteViewModel(private val fetchFavoriteUseCase: FetchFavoriteUseCase): ViewModel() {
    val movie: LiveData<List<Movie>> = fetchFavoriteUseCase.getAllFavorite().asLiveData()

    fun getPathImage(path: String): String = "https://image.tmdb.org/t/p/w500${path}"

    fun changeDateFormat(dateStr: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val date = originalFormat.parse(dateStr)
        return if (date != null) targetFormat.format(date) else "Invalid Date"
    }
}