package com.fmejiar.moviesapp.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fmejiar.moviesapp.R
import com.fmejiar.moviesapp.core.UpcomingMoviesResult
import com.fmejiar.moviesapp.core.toast
import com.fmejiar.moviesapp.data.local.AppDatabase
import com.fmejiar.moviesapp.data.local.LocalMoviesDataSource
import com.fmejiar.moviesapp.data.model.Movie
import com.fmejiar.moviesapp.data.remote.RemoteMoviesDataSource
import com.fmejiar.moviesapp.databinding.FragmentMoviesBinding
import com.fmejiar.moviesapp.domain.repository.MoviesRepositoryImpl
import com.fmejiar.moviesapp.domain.repository.RetrofitClient.webService
import com.fmejiar.moviesapp.presentation.MoviesViewModel
import com.fmejiar.moviesapp.presentation.MoviesViewModelFactory
import com.fmejiar.moviesapp.ui.adapter.UpcomingMoviesAdapter

class MoviesFragment : Fragment(), UpcomingMoviesAdapter.OnUpcomingMovieClickListener {

    private lateinit var binding: FragmentMoviesBinding
    private val moviesViewModel by viewModels<MoviesViewModel> {
        MoviesViewModelFactory(
            MoviesRepositoryImpl(
                RemoteMoviesDataSource(webService),
                LocalMoviesDataSource(AppDatabase.getDatabase(requireContext()).moviesDao())
            )
        )
    }
    private val upcomingMoviesAdapter by lazy {
        UpcomingMoviesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        setupUpcomingMoviesRecyclerView()
    }

    private fun setupUpcomingMoviesRecyclerView() {
        binding.upcomingMoviesRecyclerView.adapter = upcomingMoviesAdapter
    }

    private fun setupObserver() {
        moviesViewModel.fetchUpcomingMovies()
            .observe(viewLifecycleOwner, Observer { upcomingMoviesResult ->
                when (upcomingMoviesResult) {
                    is UpcomingMoviesResult.Loading -> {
                        Log.d("LiveData", "Loading...")
                        binding.progressBarRelativeLayout.visibility = View.VISIBLE
                    }
                    is UpcomingMoviesResult.Success -> {
                        Log.d("LiveData", "${upcomingMoviesResult.data}")
                        binding.progressBarRelativeLayout.visibility = View.GONE
                        upcomingMoviesAdapter.setUpcomingMovies(upcomingMoviesResult.data.results)
                    }
                    is UpcomingMoviesResult.Failure -> {
                        Log.d("Error", "${upcomingMoviesResult.exception}")
                        binding.progressBarRelativeLayout.visibility = View.GONE
                        requireContext().toast("Ocurrió un error en el servicio")
                    }
                }
            })
    }

    override fun onUpcomingMovieClick(movie: Movie, position: Int) {
        Log.d("UpcomingMovie", "onUpcomingMovieClick: $movie")
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(
            movie.poster_path ?: "",
            movie.vote_average?.toFloat() ?: 0.0f,
            movie.overview ?: "",
            movie.title ?: "",
            movie.release_date ?: ""
        )
        findNavController().navigate(action)
    }

}