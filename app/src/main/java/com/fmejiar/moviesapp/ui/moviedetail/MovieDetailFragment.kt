package com.fmejiar.moviesapp.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fmejiar.moviesapp.R
import com.fmejiar.moviesapp.application.AppConstants.BASE_IMAGE_URL
import com.fmejiar.moviesapp.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        setupUI()
    }

    private fun setupUI() {
        args.let {
            Glide.with(requireContext())
                .load("$BASE_IMAGE_URL${it.posterPathImageUrl}").centerCrop()
                .into(binding.posterPathDetailImageView)
            binding.titleDetailTextView.text = it.title
            binding.overviewDetailTextView.text = it.overview
            binding.voteAverageDetailTextView.text = it.voteAverage.toString()
            binding.releaseDateDetailTextView.text = "Released ${it.releaseDate}"
        }
    }

}