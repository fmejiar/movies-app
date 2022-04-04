package com.fmejiar.moviesapp.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.fmejiar.moviesapp.application.AppConstants.EMPTY_STRING
import com.fmejiar.moviesapp.domain.usecase.DoLogInUseCase
import com.fmejiar.moviesapp.domain.usecase.GetUpcomingMoviesUseCase

class MoviesFragment : Fragment(), UpcomingMoviesAdapter.OnUpcomingMovieClickListener {

    private lateinit var binding: FragmentMoviesBinding
    private val moviesViewModel by activityViewModels<MoviesViewModel> {
        MoviesViewModelFactory(
            GetUpcomingMoviesUseCase(
                MoviesRepositoryImpl(
                    RemoteMoviesDataSource(webService),
                    LocalMoviesDataSource(AppDatabase.getDatabase(requireContext()).moviesDao())
                )
            ),
            DoLogInUseCase(
                MoviesRepositoryImpl(
                    RemoteMoviesDataSource(webService),
                    LocalMoviesDataSource(AppDatabase.getDatabase(requireContext()).moviesDao())
                )
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
        setupOnBackPressedCallback()
    }

    private fun setupUpcomingMoviesRecyclerView() {
        binding.upcomingMoviesRecyclerView.adapter = upcomingMoviesAdapter
    }

    private fun setupOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun setupObserver() {
        moviesViewModel.fetchUpcomingMovies()
            .observe(viewLifecycleOwner, Observer { upcomingMoviesResult ->
                when (upcomingMoviesResult) {
                    is UpcomingMoviesResult.Loading -> {
                        binding.progressBarRelativeLayout.visibility = View.VISIBLE
                    }
                    is UpcomingMoviesResult.Success -> {
                        binding.progressBarRelativeLayout.visibility = View.GONE
                        upcomingMoviesAdapter.setUpcomingMovies(upcomingMoviesResult.data.results)
                    }
                    is UpcomingMoviesResult.Failure -> {
                        binding.progressBarRelativeLayout.visibility = View.GONE
                        showApiErrorMessage()
                    }
                }
            })
    }

    override fun onUpcomingMovieClick(movie: Movie, position: Int) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(
            movie.poster_path ?: EMPTY_STRING,
            movie.vote_average?.toFloat() ?: 0.0f,
            movie.overview ?: EMPTY_STRING,
            movie.title ?: EMPTY_STRING,
            movie.release_date ?: EMPTY_STRING
        )
        findNavController().navigate(action)
    }

    private fun showApiErrorMessage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(EMPTY_STRING)
        builder.setMessage(getString(R.string.movies_service_error_message))
        builder.setPositiveButton(getString(R.string.login_understand)) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}