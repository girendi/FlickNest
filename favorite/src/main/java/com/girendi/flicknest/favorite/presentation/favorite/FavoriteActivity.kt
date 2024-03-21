package com.girendi.flicknest.favorite.presentation.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.girendi.flicknest.core.R
import com.girendi.flicknest.core.databinding.ItemListMovieBinding
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.favorite.databinding.ActivityFavoriteBinding
import com.girendi.flicknest.favorite.di.favoriteViewModelModule
import com.girendi.flicknest.presentation.detail.DetailMovieActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import kotlin.math.round

class FavoriteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapterFavorite: SimpleRecyclerAdapter<Movie>
    private val viewModelFavorite: FavoriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        loadKoinModules(favoriteViewModelModule)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapterFavorite = SimpleRecyclerAdapter(
            context = this,
            layoutResId = R.layout.item_list_movie,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListMovieBinding.bind(view)
                itemBinding.tvTitle.text = item.title
                itemBinding.tvVote.text = item.voteAverage?.let { round(it).toString() }
                itemBinding.tvDateTime.text =
                    item.releaseDate?.let { viewModelFavorite.changeDateFormat(it) }
                Glide.with(this)
                    .load(item.posterPath?.let { viewModelFavorite.getPathImage(it) })
                    .into(itemBinding.imageView)
                itemBinding.root.setOnClickListener {
                    val intent = Intent(this, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, item.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvListFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = adapterFavorite
        }
    }

    private fun observeViewModel() {
        viewModelFavorite.movie.observe(this) {
            adapterFavorite.setListItem(it)
        }
    }
}