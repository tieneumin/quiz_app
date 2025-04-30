package com.example.quizapp.ui.home.student

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.ui.home.base.BaseHomeFragment
import dagger.hilt.android.AndroidEntryPoint

// tabs: QuizJoinFragment, ProfileFragment; HomeStudentViewModel handles QuizJoinFragment logic
@AndroidEntryPoint
class HomeStudentFragment : BaseHomeFragment() {
    override val viewModel: HomeStudentViewModel by viewModels()

    override fun navigateLogout() {
        findNavController().navigate(
            HomeStudentFragmentDirections.actionHomeStudentToLogin()
        )
    }
}