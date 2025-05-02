package com.example.quizapp.ui.home.student

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// handles QuizJoin/StudentProfileFragment logic; getQuizById (verify quiz exists)
@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val repo: QuizRepo,
    authService: AuthService
) : BaseHomeViewModel(authService) {
}