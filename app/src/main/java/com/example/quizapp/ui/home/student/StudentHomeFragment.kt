package com.example.quizapp.ui.home.student

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.ui.home.base.BaseHomeFragment
import com.example.quizapp.ui.home.base.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentHomeFragment() : BaseHomeFragment() {
    override val viewModel: StudentHomeViewModel by viewModels()
    override val fragments = listOf(JoinQuizFragment(), ProfileFragment())
    override val tabTexts = listOf("Join Quiz", "Profile")
    override val tabIcons = listOf(R.drawable.ic_clipboard_join, R.drawable.ic_profile)
    override fun onLogoutNavigate() {
        val action = StudentHomeFragmentDirections.actionStudentHomeToLogin()
        findNavController().navigate(action)
    }
}