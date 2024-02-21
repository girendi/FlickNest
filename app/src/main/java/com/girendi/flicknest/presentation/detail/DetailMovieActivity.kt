package com.girendi.flicknest.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.girendi.flicknest.R
import com.girendi.flicknest.data.models.Review
import com.girendi.flicknest.data.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityDetailMovieBinding
import com.girendi.flicknest.databinding.ItemListReviewBinding
import kotlin.math.round

class DetailMovieActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var adapterReview: SimpleRecyclerAdapter<Review>
    private val viewModel by viewModels<DetailMovieViewModel>()
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
                val datetime = viewModel.reformatDateString(item.createdAt)
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
                    val visibleThreshold = 5
                    val layoutManager: LinearLayoutManager =
                        binding.rvListReview.layoutManager as LinearLayoutManager
                    val lastItemVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if ((currentTotalCount <= (lastItemVisible + visibleThreshold)) && !binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = true
                        movieId?.let { viewModel.getListReview(it) }
                    }
                }
            }
        )

        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        viewModel.movie.observe(this) {
            if (it != null) {
                binding.tvTitle.text = it.title
                binding.tvDateTime.text = it.releaseDate?.let { time ->
                    viewModel.changeDateFormat(
                        time
                    )
                }
                binding.tvQuote.text = it.tagline
                val vote = it.voteAverage?.let { average -> round(average).toString() }
                binding.tvVote.text = "User Score $vote/10"
                binding.tvOverview.text = it.overview
                Glide.with(this)
                    .load(it.posterPath?.let { path ->
                        viewModel.getPathImage(
                            path
                        )
                    })
                    .into(binding.imageView)
                supportActionBar?.title = it.title
            }
        }
        viewModel.listReview.observe(this) {
            binding.swipeRefreshLayout.isRefreshing = false
            adapterReview.setListItem(it)
        }
        viewModel.listVideo.observe(this) { trailer ->
            if (trailer != null && trailer.isNotEmpty()) {
                playYoutubeVideo(trailer[0].key)
            }
        }
        movieId?.let {
            viewModel.getDetailMovie(it)
            viewModel.resetGetListReview(it)
        }
    }

    private fun setupOnClick() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.cvTrailer.setOnClickListener {
            movieId?.let { id -> viewModel.fetchMovieTrailers(id) }
        }
    }

    private fun playYoutubeVideo(videoKey: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoKey"))
        startActivity(intent)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override fun onRefresh() {
        binding.swipeRefreshLayout.isRefreshing = true
        movieId?.let { viewModel.resetGetListReview(it) }
    }
}