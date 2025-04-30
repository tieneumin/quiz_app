package com.example.quizapp.ui.home.teacher

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.ui.home.base.BaseHomeFragment
import dagger.hilt.android.AndroidEntryPoint

// tabs: QuizzesFragment, ProfileFragment; HomeTeacherViewModel handles QuizzesFragment logic
@AndroidEntryPoint
class HomeTeacherFragment : BaseHomeFragment() {
    override val viewModel: HomeTeacherViewModel by viewModels()

    override fun navigateLogout() {
        findNavController().navigate(
            HomeTeacherFragmentDirections.actionHomeTeacherToLogin()
        )
    }
}