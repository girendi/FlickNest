package com.girendi.flicknest.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.girendi.flicknest.R
import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Review
import com.girendi.flicknest.data.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityDetailMovieBinding
import com.girendi.flicknest.databinding.ItemListReviewBinding
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class DetailMovieActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var adapterReview: SimpleRecyclerAdapter<Review>
    private val viewModelMovie: DetailMovieViewModel by viewModel()
    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieId = intent.getIntExtra(EXTRA_ID, 0)

        setupOnClick()
        observeViewModel()
        setupRecyclerView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupRecyclerView() {
        adapterReview = SimpleRecyclerAdapter(
            context = this,
            layoutResId = R.layout.item_list_review,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListReviewBinding.bind(view)
                val datetime = viewModelMovie.reformatDateString(item.createdAt)
                itemBinding.tvName.text = item.author
                itemBinding.tvDate.text = "Written on $datetime"
                itemBinding.tvContent.text = item.content
            }
        )
        binding.rvListReview.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterReview
        }
        binding.rvListReview.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!binding.rvListReview.canScrollVertically(1)) {
                        movieId?.let { viewModelMovie.fetchMovieReviews(it) }
                    }
                }
            }
        )

        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModelMovie.resultMovie.observe(this) { result ->
            when(result) {
                is Result.Success -> {
                    showLoading(false)
                    showMovieDetail(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    showError(result.exception.message ?: "An error occurred")
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }
        }
        viewModelMovie.resultVideos.observe(this) { result ->
            when(result) {
                is Result.Success -> {
                    showLoading(false)
                    val trailers = result.data.filter {
                        it.type == "Trailer"
                    }
                    if (trailers.isNotEmpty()) {
                        playYoutubeVideo(trailers[0].key)
                    }
                }
                is Result.Error -> {
                    showLoading(false)
                    showError(result.exception.message ?: "An error occurred")
                }
                is Result.Loading -> {
                    showLoading(true)
                }
            }
        }
        viewModelMovie.uiState.observe(this) { state ->
            handleUiState(state)
        }
        viewModelMovie.listReview.observe(this) { reviews ->
            handleEmptyResult(reviews.isEmpty())
            adapterReview.setListItem(reviews)
        }
        movieId?.let {
            viewModelMovie.fetchMovieDetail(it)
            viewModelMovie.fetchMovieReviews(it)
        }
    }

    private fun handleEmptyResult(empty: Boolean) {
        binding.rvListReview.visibility = if (empty) View.GONE else View.VISIBLE
        binding.tvEmpty.visibility = if (empty) View.VISIBLE else View.GONE
    }

    private fun handleUiState(state: UiState) {
        when (state) {
            is UiState.Loading -> showRefreshLayout(true)
            is UiState.Success -> showRefreshLayout(false)
            is UiState.Error -> {
                showRefreshLayout(false)
                handleEmptyResult(true)
                showError(state.message)
            }
        }
    }

    private fun showMovieDetail(data: Movie) {
        binding.tvTitle.text = data.title
        binding.tvDateTime.text = data.releaseDate?.let { time ->
            viewModelMovie.changeDateFormat(
                time
            )
        }
        binding.tvQuote.text = data.tagline
        val vote = data.voteAverage?.let { average -> round(average).toString() }
        binding.tvVote.text = "User Score $vote/10"
        binding.tvOverview.text = data.overview
        Glide.with(this)
            .load(data.posterPath?.let { path ->
                viewModelMovie.getPathImage(
                    path
                )
            })
            .into(binding.imageView)
        supportActionBar?.title = data.title
    }

    private fun setupOnClick() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.cvTrailer.setOnClickListener {
            movieId?.let { id -> viewModelMovie.fetchMovieVideos(id) }
        }
    }

    private fun playYoutubeVideo(videoKey: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoKey"))
        startActivity(intent)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showRefreshLayout(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onRefresh() {
        movieId?.let { viewModelMovie.fetchMovieReviews(it) }
    }
}