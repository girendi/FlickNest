package com.girendi.flicknest.presentation.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girendi.flicknest.data.model.Genre
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.usecase.FetchGenreUseCase
import kotlinx.coroutines.launch

class GenreViewModel(private val fetchGenreUseCase: FetchGenreUseCase) : ViewModel() {
    private val _listGenre = MutableLiveData<Result<List<Genre>>>()
    val listGenre: LiveData<Result<List<Genre>>> = _listGenre

    init {
        fetchGenres()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            _listGenre.value = Result.Loading
            _listGenre.value = fetchGenreUseCase.execute()
        }
    }
}