package com.girendi.flicknest.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.girendi.flicknest.R
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.FragmentHomeBinding
import com.girendi.flicknest.core.data.UiState
import com.girendi.flicknest.core.databinding.ItemListMostPopularBinding
import com.girendi.flicknest.presentation.detail.DetailMovieActivity
import com.girendi.flicknest.presentation.movie.ListMovieActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterTrending: SimpleRecyclerAdapter<Movie>
    private lateinit var adapterPopular: SimpleRecyclerAdapter<Movie>
    private val viewModelHome: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupOnClick()
    }

    private fun setupOnClick() {
        binding.tvSeeMoreTrending.setOnClickListener {
            handleClickSeeMore(resources.getString(R.string.trending))
        }
        binding.tvSeeMoreMostPopular.setOnClickListener {
            handleClickSeeMore(resources.getString(R.string.most_populars))
        }
        binding.ivFavorite.setOnClickListener {
            try {
                installFavoriteModule()
            } catch (e: Exception) {
                Toast.makeText(requireContext(),
                    getString(R.string.module_not_found), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(requireContext())
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),
                        getString(R.string.error_installing_module), Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        startActivity(Intent(requireContext(), Class.forName("com.girendi.flicknest.favorite.presentation.favorite.FavoriteActivity")))
    }

    private fun handleClickSeeMore(request: String) {
        val intent = Intent(requireContext(), ListMovieActivity::class.java)
        intent.putExtra(ListMovieActivity.EXTRA_REQUEST, request)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        adapterTrending = SimpleRecyclerAdapter(
            context = requireContext(),
            layoutResId = com.girendi.flicknest.core.R.layout.item_list_most_popular,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListMostPopularBinding.bind(view)
                itemBinding.tvTitle.text = item.title
                Glide.with(this)
                    .load(item.posterPath?.let { path ->
                        viewModelHome.getBackdropPathImage(
                            path
                        )
                    })
                    .into(itemBinding.imageView)
                itemBinding.root.setOnClickListener {
                    val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, item.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvMostTrending.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = adapterTrending
        }

        adapterPopular = SimpleRecyclerAdapter(
            context = requireContext(),
            layoutResId = com.girendi.flicknest.core.R.layout.item_list_most_popular,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListMostPopularBinding.bind(view)
                itemBinding.tvTitle.text = item.title
                Glide.with(this)
                    .load(item.posterPath?.let { path ->
                        viewModelHome.getBackdropPathImage(
                            path
                        )
                    })
                    .into(itemBinding.imageView)
                itemBinding.root.setOnClickListener {
                    val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, item.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvMostPopulars.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adapterPopular
        }
    }

    private fun observeViewModel() {
        viewModelHome.trending.observe(viewLifecycleOwner) { result ->
            adapterTrending.setListItem(result)
        }
        viewModelHome.mostTrending.observe(viewLifecycleOwner) { result ->
            showMostTrendingMovie(result)
        }
        viewModelHome.uiState.observe(viewLifecycleOwner) { state ->
            handleUiState(state)
        }
        viewModelHome.resultMovie.observe(viewLifecycleOwner) { movies ->
            adapterPopular.setListItem(movies)
        }
        viewModelHome.video.observe(viewLifecycleOwner) { video ->
            if (video != null) playYoutubeVideo(video.key)
        }
    }

    private fun showMostTrendingMovie(data: Movie) {
        binding.tvTitleMovie.text = data.title
        binding.tvTagLine.text = data.tagline
        binding.tvDateTime.text = data.releaseDate?.let { viewModelHome.changeDateFormat(it) }
        Glide.with(this)
            .load(data.backdropPath?.let { path ->
                viewModelHome.getBackdropPathImage(
                    path
                )
            })
            .into(binding.ivMostPopular)
        binding.clHeader.setOnClickListener {
            val intent = Intent(requireContext(), DetailMovieActivity::class.java)
            intent.putExtra(DetailMovieActivity.EXTRA_ID, data.id)
            startActivity(intent)
        }
        binding.ivPlayTrailer.setOnClickListener {
            viewModelHome.fetchMovieVideos(data.id)
        }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playYoutubeVideo(videoKey: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoKey"))
        startActivity(intent)
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}