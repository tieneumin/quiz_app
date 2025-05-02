package com.example.quizapp.ui.home.teacher

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.ui.home.base.BaseHomeFragment
import com.example.quizapp.ui.home.base.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

// tabs: QuizzesFragment, ProfileFragment; HomeTeacherViewModel handles QuizzesFragment logic
@AndroidEntryPoint
class HomeTeacherFragment : BaseHomeFragment(), ProfileFragment.LogoutHandler {
    override val viewModel: HomeTeacherViewModel by viewModels()

    override val fragments = listOf(QuizzesFragment(), ProfileFragment())
    override val tabTexts = listOf("Quizzes", "Profile")
    override val tabIcons = listOf(R.drawable.ic_clipboard, R.drawable.ic_profile)

    override fun navigateToLogin() {
        val action = HomeTeacherFragmentDirections.actionHomeTeacherToLogin()
        findNavController().navigate(action)
    }
}