package com.fmejiar.moviesapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fmejiar.moviesapp.R
import com.fmejiar.moviesapp.data.local.AppDatabase
import com.fmejiar.moviesapp.data.local.LocalMoviesDataSource
import com.fmejiar.moviesapp.data.remote.RemoteMoviesDataSource
import com.fmejiar.moviesapp.databinding.FragmentLogInBinding
import com.fmejiar.moviesapp.domain.repository.MoviesRepositoryImpl
import com.fmejiar.moviesapp.domain.repository.RetrofitClient
import com.fmejiar.moviesapp.domain.usecase.DoLogInUseCase
import com.fmejiar.moviesapp.domain.usecase.GetUpcomingMoviesUseCase
import com.fmejiar.moviesapp.presentation.MoviesViewModel
import com.fmejiar.moviesapp.presentation.MoviesViewModelFactory
import kotlinx.coroutines.launch

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val moviesViewModel by activityViewModels<MoviesViewModel> {
        MoviesViewModelFactory(
            GetUpcomingMoviesUseCase(
                MoviesRepositoryImpl(
                    RemoteMoviesDataSource(RetrofitClient.webService),
                    LocalMoviesDataSource(AppDatabase.getDatabase(requireContext()).moviesDao())
                )
            ),
            DoLogInUseCase(
                MoviesRepositoryImpl(
                    RemoteMoviesDataSource(RetrofitClient.webService),
                    LocalMoviesDataSource(AppDatabase.getDatabase(requireContext()).moviesDao())
                )
            )
        )
    }
    private var isLogInSuccesful: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLogInBinding.bind(view)
        setupUI()
    }

    private fun setupUI() {
        binding.buttonLogIn.setOnClickListener {
            val user = binding.textInputLayoutUsername.editText?.text.toString()
            val password = binding.textInputLayoutPassword.editText?.text.toString()
            if (validateForm(user, password)) doLogin(user, password) // navigateToHome()
        }
    }

    private fun validateForm(user: String, password: String): Boolean {
        clear()
        if (user.isEmpty()) {
            binding.textInputLayoutUsername.error = "Ingresar el nombre"
            return false
        }
        if (password.isEmpty()) {
            binding.textInputLayoutPassword.error = "Ingresar el password"
            return false
        }
        return true
    }

    private fun clear() {
        binding.textInputLayoutUsername.error = null
        binding.textInputLayoutPassword.error = null
    }

    private fun resetInputLayouts() {
        binding.textInputLayoutUsername.editText?.text?.clear()
        binding.textInputLayoutPassword.editText?.text?.clear()
        binding.textInputLayoutUsername.editText?.requestFocus()
    }

    private fun doLogin(user: String, password: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            isLogInSuccesful = moviesViewModel.doLogin(user, password)
        }
        if(isLogInSuccesful) navigateToHome()
        else showErrorMessage()
    }

    private fun showErrorMessage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("")
        builder.setMessage("El nombre de usuario o contraseña es incorrecto.")
        builder.setPositiveButton("Entiendo") { _, _ ->
            resetInputLayouts()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_logInFragment_to_moviesFragment)
    }

}