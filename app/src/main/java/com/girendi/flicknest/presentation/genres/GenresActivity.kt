package com.girendi.flicknest.presentation.genres

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.girendi.flicknest.R
import com.girendi.flicknest.data.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityGenresBinding
import com.girendi.flicknest.data.model.Genre
import com.girendi.flicknest.databinding.ItemListGenresBinding
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.presentation.movie.ListMovieActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGenresBinding
    private lateinit var adapterGenre: SimpleRecyclerAdapter<Genre>
    private val genreViewModel: GenreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        genreViewModel.listGenre.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    handleViewContent("", false)
                    adapterGenre.setListItem(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    handleViewContent(result.exception.message ?: "An error occurred", true)
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun handleViewContent(message: String, status: Boolean) {
        binding.errorMessage.text = message
        binding.rvListGenres.visibility = if (status) View.GONE else View.VISIBLE
        binding.contentError.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        adapterGenre = SimpleRecyclerAdapter(
            context = this,
            layoutResId = R.layout.item_list_genres,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListGenresBinding.bind(view)
                itemBinding.tvTitle.text = item.name
                itemBinding.root.setOnClickListener {
                    val intent = Intent(applicationContext, ListMovieActivity::class.java)
                    intent.putExtra(ListMovieActivity.EXTRA_GENRE, item)
                    startActivity(intent)
                }
            }
        )
        binding.rvListGenres.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterGenre
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}