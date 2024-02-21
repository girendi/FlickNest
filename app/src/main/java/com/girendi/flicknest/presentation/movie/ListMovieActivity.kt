package com.girendi.flicknest.presentation.movie


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.girendi.flicknest.R
import com.girendi.flicknest.data.models.Genre
import com.girendi.flicknest.data.models.Movie
import com.girendi.flicknest.data.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.ActivityListMovieBinding
import com.girendi.flicknest.databinding.ItemListMovieBinding
import com.girendi.flicknest.presentation.detail.DetailMovieActivity
import kotlin.math.round

class ListMovieActivity: AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityListMovieBinding
    private lateinit var adapterMovie: SimpleRecyclerAdapter<Movie>
    private val viewModel by viewModels<ListMovieViewModel>()
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
        viewModel.listMovie.observe(this) {
            binding.swipeRefreshLayout.isRefreshing = false
            adapterMovie.setListItem(it)
        }
        viewModel.resetGetListMovie(genre?.id.toString())
    }

    private fun setupRecyclerView() {
        adapterMovie = SimpleRecyclerAdapter(
            context = this,
            layoutResId = R.layout.item_list_movie,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListMovieBinding.bind(view)
                itemBinding.tvTitle.text = item.title
                itemBinding.tvVote.text = item.voteAverage?.let { round(it).toString() }
                itemBinding.tvDateTime.text = item.releaseDate?.let { viewModel.changeDateFormat(it) }
                Glide.with(this)
                    .load(item.posterPath?.let { viewModel.getPathImage(it) })
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
                    val visibleThreshold = 5
                    val layoutManager: LinearLayoutManager =
                        binding.rvListMovie.layoutManager as LinearLayoutManager
                    val lastItemVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if ((currentTotalCount <= (lastItemVisible + visibleThreshold)) && !binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = true
                        viewModel.getListMovie(genre?.id.toString())
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

    companion object {
        const val EXTRA_GENRE = "extra_genre"
    }

    override fun onRefresh() {
        binding.swipeRefreshLayout.isRefreshing = true
        viewModel.resetGetListMovie(genre?.id.toString())
    }
}