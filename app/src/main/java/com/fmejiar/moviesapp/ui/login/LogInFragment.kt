package com.fmejiar.moviesapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fmejiar.moviesapp.R
import com.fmejiar.moviesapp.core.isValidEmail
import com.fmejiar.moviesapp.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

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
            if(validateForm()) navigateToHome()
        }
    }

    private fun validateForm():Boolean {
        clear()
        val username = binding.textInputLayoutUsername.editText?.text.toString()
        val password = binding.textInputLayoutPassword.editText?.text.toString()
        if(username.isEmpty()) {
            binding.textInputLayoutUsername.error = "Ingresar el nombre"
            return false
        }
        if(!username.isValidEmail()) {
            binding.textInputLayoutUsername.error = "E-mail inválido"
            return false
        }

        if(password.isEmpty()) {
            binding.textInputLayoutPassword.error = "Ingresar el password"
            return false
        }
        return true
    }

    private fun clear() {
        binding.textInputLayoutUsername.error = null
        binding.textInputLayoutPassword.error = null
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_logInFragment_to_moviesFragment)
    }

}