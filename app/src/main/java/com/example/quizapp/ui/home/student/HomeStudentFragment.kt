package com.example.quizapp.ui.home.student

import com.example.quizapp.R
import com.example.quizapp.ui.home.base.BaseHomeFragment
import com.example.quizapp.ui.home.base.ProfileFragment

// tabs: QuizJoinFragment, ProfileFragment; HomeStudentViewModel handles QuizJoinFragment logic
class HomeStudentFragment() : BaseHomeFragment() {
    override val fragments = listOf(QuizJoinFragment(), ProfileFragment())
    override val tabTexts = listOf("Join Quiz", "Profile")
    override val tabIcons = listOf(R.drawable.ic_clipboard_join, R.drawable.ic_profile)
}