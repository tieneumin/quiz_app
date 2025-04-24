package com.example.quizapp.ui.home.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.quizapp.R
import com.example.quizapp.ui.home.base.BaseHomeFragment
import com.example.quizapp.ui.home.base.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

// tabs: QuizzesFragment, ProfileFragment; HomeTeacherViewModel handles QuizzesFragment logic
@AndroidEntryPoint
class HomeTeacherFragment : BaseHomeFragment() {
    override val fragments = listOf(QuizzesFragment(), ProfileFragment())
    override val tabTexts = listOf("Quizzes", "Profile")
    override val tabIcons = listOf(R.drawable.ic_clipboard, R.drawable.ic_profile)
    private val viewModel: HomeTeacherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}