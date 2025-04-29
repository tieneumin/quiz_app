package com.example.quizapp.ui.auth.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.ui.auth.BaseAuthFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseAuthFragment() {
    override val viewModel: LoginViewModel by viewModels()

    override fun setupUiComponents() {
        binding.run {
            tvAuth.text = getString(R.string.login)
            tilConfirmPass.isVisible = false
            btnAuth.text = getString(R.string.login)
            btnAuth.setOnClickListener {
                val email = etEmail.text.toString()
                val pass = etPass.text.toString()
                viewModel.login(email, pass)
            }
            btnGoogle.setOnClickListener { viewModel.loginWithGoogle(requireContext()) }
            chipAuth.text = getString(R.string.sign_up)
            chipAuth.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToSignUp()
                findNavController().navigate(action)
            }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.success.collect {
                viewModel.getUserRole().let { role ->
                    val action = when (role) {
                        "student" -> LoginFragmentDirections.actionToHomeStudent()
                        "teacher" -> LoginFragmentDirections.actionLoginToHomeTeacher()
                        else -> LoginFragmentDirections.actionLoginToSignUp()
                    }
                    findNavController().navigate(action)
                }
            }
        }
    }
}