package com.girendi.flicknest.presentation.genres

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.girendi.flicknest.data.models.Genre
import com.girendi.flicknest.data.api.ApiConfig
import com.girendi.flicknest.data.response.ListGenresResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreViewModel(application: Application) : AndroidViewModel(application) {
    private val _listGenre = MutableLiveData<List<Genre>>()
    val listGenre: LiveData<List<Genre>> = _listGenre

    fun getListGenre() {
        ApiConfig.provideApiService().getList().enqueue(
            object : Callback<ListGenresResponse> {
                override fun onResponse(
                    call: Call<ListGenresResponse>,
                    response: Response<ListGenresResponse>
                ) {
                    if (response.isSuccessful) {
                        _listGenre.postValue(response.body()?.listGenre)
                    }
                }

                override fun onFailure(call: Call<ListGenresResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            }
        )
    }
}