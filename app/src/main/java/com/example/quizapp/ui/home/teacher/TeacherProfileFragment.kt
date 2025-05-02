package com.example.quizapp.ui.home.teacher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.quizapp.ui.home.base.BaseProfileFragment
import com.example.quizapp.ui.home.student.StudentHomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherProfileFragment() : BaseProfileFragment() {
    private val viewModel: TeacherHomeViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            Glide.with(ivPhoto).load(viewModel.getUserPhoto()).into(ivPhoto)
            btnLogout.setOnClickListener {
                viewModel.logout()
                val action = TeacherHomeFragmentDirections.actionTeacherHomeToLogin()
                findNavController().navigate(action)
            }
        }
    }
}