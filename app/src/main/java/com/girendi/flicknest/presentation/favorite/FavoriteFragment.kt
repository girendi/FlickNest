package com.girendi.flicknest.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.girendi.flicknest.core.R
import com.girendi.flicknest.core.databinding.ItemListMovieBinding
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.ui.SimpleRecyclerAdapter
import com.girendi.flicknest.databinding.FragmentFavoriteBinding
import com.girendi.flicknest.presentation.detail.DetailMovieActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.round

class FavoriteFragment: Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapterFavorite: SimpleRecyclerAdapter<Movie>
    private val viewModelFavorite: FavoriteViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapterFavorite = SimpleRecyclerAdapter(
            context = requireContext(),
            layoutResId = R.layout.item_list_movie,
            bindViewHolder = { view, item ->
                val itemBinding = ItemListMovieBinding.bind(view)
                itemBinding.tvTitle.text = item.title
                itemBinding.tvVote.text = item.voteAverage?.let { round(it).toString() }
                itemBinding.tvDateTime.text =
                    item.releaseDate?.let { viewModelFavorite.changeDateFormat(it) }
                Glide.with(requireContext())
                    .load(item.posterPath?.let { viewModelFavorite.getPathImage(it) })
                    .into(itemBinding.imageView)
                itemBinding.root.setOnClickListener {
                    val intent = Intent(requireContext(), DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, item.id)
                    startActivity(intent)
                }
            }
        )
        binding.rvListFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterFavorite
        }
    }

    private fun observeViewModel() {
        viewModelFavorite.movie.observe(viewLifecycleOwner) {
            adapterFavorite.setListItem(it)
        }
    }
}