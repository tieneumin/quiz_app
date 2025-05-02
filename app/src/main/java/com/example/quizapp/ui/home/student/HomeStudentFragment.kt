package com.example.quizapp.ui.home.student

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.ui.home.base.BaseHomeFragment
import com.example.quizapp.ui.home.base.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

// tabs: QuizJoinFragment, ProfileFragment; HomeStudentViewModel handles QuizJoinFragment logic
@AndroidEntryPoint
class HomeStudentFragment() : BaseHomeFragment(), ProfileFragment.LogoutHandler {
    override val viewModel: HomeStudentViewModel by viewModels()

    override val fragments = listOf(QuizJoinFragment(), ProfileFragment())
    override val tabTexts = listOf("Join Quiz", "Profile")
    override val tabIcons = listOf(R.drawable.ic_clipboard_join, R.drawable.ic_profile)

    override fun navigateToLogin() {
        val action = HomeStudentFragmentDirections.actionHomeStudentToLogin()
        findNavController().navigate(action)
    }
}