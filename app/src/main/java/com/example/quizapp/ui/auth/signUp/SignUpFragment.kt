package com.example.quizapp.ui.auth.signUp

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.ui.auth.BaseAuthFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseAuthFragment() {
    override val viewModel: SignUpViewModel by viewModels()

    override fun setupUiComponents() {
        binding.run {
            tvAuth.text = getString(R.string.sign_up)
            btnAuth.text = getString(R.string.sign_up)
            btnAuth.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val pass = etPass.text.toString().trim()
                val confirmPass = etConfirmPass.text.toString().trim()
                viewModel.register(email, pass, confirmPass)
            }
            btnGoogle.isVisible = false
            chipAuth.text = getString(R.string.login)
            chipAuth.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.success.collect {
                findNavController().navigate(
                    SignUpFragmentDirections.actionToStudentHome()
                )
            }
        }
    }
}