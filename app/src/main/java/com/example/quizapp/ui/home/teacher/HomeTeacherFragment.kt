package com.example.quizapp.ui.home.teacher

import com.example.quizapp.R
import com.example.quizapp.ui.home.base.BaseHomeFragment
import com.example.quizapp.ui.home.base.ProfileFragment

// tabs: QuizzesFragment, ProfileFragment; HomeTeacherViewModel handles QuizzesFragment logic
class HomeTeacherFragment : BaseHomeFragment() {
    override val fragments = listOf(QuizzesFragment(), ProfileFragment())
    override val tabTexts = listOf("Quizzes", "Profile")
    override val tabIcons = listOf(R.drawable.ic_clipboard, R.drawable.ic_profile)
}