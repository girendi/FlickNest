package com.girendi.flicknest.presentation.genres

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.girendi.flicknest.R
import com.girendi.flicknest.data.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityGenresBinding
import com.girendi.flicknest.data.models.Genre
import com.girendi.flicknest.databinding.ItemListGenresBinding
import com.girendi.flicknest.presentation.movie.ListMovieActivity

class GenresActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGenresBinding
    private lateinit var adapterGenre: SimpleRecyclerAdapter<Genre>
    private val viewModel by viewModels<GenreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.listGenre.observe(this) {
            adapterGenre.setListItem(it)
        }
        viewModel.getListGenre()
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

}