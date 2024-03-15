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
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.model.Review
import com.girendi.flicknest.core.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityDetailMovieBinding
import com.girendi.flicknest.core.data.UiState
import com.girendi.flicknest.core.databinding.ItemListReviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class DetailMovieActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var adapterReview: SimpleRecyclerAdapter<Review>
    private val viewModelMovie: DetailMovieViewModel by viewModel()
    private var movieId: Int? = null
    private var movie: Movie? = null

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
            layoutResId = com.girendi.flicknest.core.R.layout.item_list_review,
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
                        movieId?.let { viewModelMovie.fetchReviewByMovie(it) }
                    }
                }
            }
        )

        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModelMovie.movie.observe(this) { movie ->
            if (movie != null) {
                this.movie = movie
                showMovieDetail(movie)
                handleViewContent("", false)
            }
        }
        viewModelMovie.video.observe(this) { video ->
            if (video != null) playYoutubeVideo(video.key)
        }
        viewModelMovie.uiState.observe(this) { state ->
            handleUiState(state)
        }
        viewModelMovie.listReview.observe(this) { reviews ->
            handleEmptyResult(reviews.isEmpty())
            adapterReview.setListItem(reviews)
        }
        viewModelMovie.error.observe(this) { message ->
            handleViewContent(message, true)
            supportActionBar?.title = resources.getString(R.string.something_went_wrong)
        }
        movieId?.let {
            viewModelMovie.fetchMovieDetail(it)
            viewModelMovie.fetchReviewByMovie(it)
            viewModelMovie.getFavoriteMovieById(it).observe(this) { favoriteMovie ->
                if (favoriteMovie != null) {
                    binding.icFavorite.apply {
                        setImageResource(R.drawable.ic_favorite_white)
                        setOnClickListener {
                            viewModelMovie.deleteFavoriteMovie(favoriteMovie)
                        }
                    }
                } else {
                    binding.icFavorite.apply {
                        setImageResource(R.drawable.ic_unfavorite_white)
                        setOnClickListener {
                            movie?.let { movie -> viewModelMovie.insertFavoriteMovie(movie) }
                        }
                    }
                }
            }
        }
    }

    private fun handleViewContent(message: String, status: Boolean) {
        binding.errorMessage.text = message
        binding.contentView.visibility = if (status) View.GONE else View.VISIBLE
        binding.contentError.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun handleEmptyResult(empty: Boolean) {
        binding.rvListReview.visibility = if (empty) View.GONE else View.VISIBLE
        binding.tvEmpty.visibility = if (empty) View.VISIBLE else View.GONE
    }

    private fun handleUiState(state: UiState) {
        when (state) {
            is UiState.Loading -> showLoading(true)
            is UiState.Success -> {
                showLoading(false)
                showRefreshLayout(false)
            }
            is UiState.Error -> {
                showLoading(false)
                showRefreshLayout(false)
                handleEmptyResult(true)
                showError(state.message)
            }
        }
    }

    @SuppressLint("SetTextI18n")
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
            onBackPressedDispatcher.onBackPressed()
        }
        binding.cvTrailer.setOnClickListener {
            movieId?.let { id -> viewModelMovie.fetchVideoByMovie(id) }
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
        movieId?.let {
            showRefreshLayout(true)
            viewModelMovie.fetchReviewByMovie(it)
        }
    }
}