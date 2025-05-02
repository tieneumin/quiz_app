package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.quizapp.ui.home.base.BaseProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentProfileFragment() : BaseProfileFragment() {
    private val viewModel: StudentHomeViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            Glide.with(ivPhoto).load(viewModel.getUserPhoto()).into(ivPhoto)
            btnLogout.setOnClickListener {
                viewModel.logout()
                val action = StudentHomeFragmentDirections.actionStudentHomeToLogin()
                findNavController().navigate(action)
            }
        }
    }
}