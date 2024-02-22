package com.girendi.flicknest.presentation.movie


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.girendi.flicknest.R
import com.girendi.flicknest.data.model.Genre
import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityListMovieBinding
import com.girendi.flicknest.databinding.ItemListMovieBinding
import com.girendi.flicknest.domain.UiState
import com.girendi.flicknest.presentation.detail.DetailMovieActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class ListMovieActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityListMovieBinding
    private lateinit var adapterMovie: SimpleRecyclerAdapter<Movie>
    private val movieViewModel: ListMovieViewModel by viewModel()
    private var genre: Genre? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        genre = intent.getParcelableExtra(EXTRA_GENRE)
        supportActionBar?.title = genre?.name

        setupOnClick()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        movieViewModel.uiState.observe(this) { state ->
            handleUiState(state)
        }
        movieViewModel.listMovie.observe(this) { movie ->
            adapterMovie.setListItem(movie)
        }
        movieViewModel.fetchMovieByGenre(genre?.id.toString())
    }

    private fun handleUiState(state: UiState) {
        when (state) {
            is UiState.Loading -> showLoading(true)
            is UiState.Success -> showLoading(false)
            is UiState.Error -> {
                showLoading(false)
                showError(state.message)
            }
        }
    }

    private fun setupRecyclerView() {
        adapterMovie = SimpleRecyclerAdapter(
            context = this,
            layoutResId = R.layout.item_list_movie,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListMovieBinding.bind(view)
                itemBinding.tvTitle.text = item.title
                itemBinding.tvVote.text = item.voteAverage?.let { round(it).toString() }
                itemBinding.tvDateTime.text = item.releaseDate?.let { movieViewModel.changeDateFormat(it) }
                Glide.with(this)
                    .load(item.posterPath?.let { movieViewModel.getPathImage(it) })
                    .into(itemBinding.imageView)
                itemBinding.root.setOnClickListener {
                    val intent = Intent(applicationContext, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, item.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvListMovie.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterMovie
        }

        binding.rvListMovie.addOnScrollListener(
            object :RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!binding.rvListMovie.canScrollVertically(1)) {
                        movieViewModel.fetchMovieByGenre(genre?.id.toString())
                    }
                }
            }
        )

        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun setupOnClick() {
        binding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_GENRE = "extra_genre"
    }

    override fun onRefresh() {
        movieViewModel.fetchMovieByGenre(genre?.id.toString())
    }
}