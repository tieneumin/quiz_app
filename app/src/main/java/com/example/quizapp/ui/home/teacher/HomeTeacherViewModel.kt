package com.example.quizapp.ui.home.teacher

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// getQuizzes flow, delete
@HiltViewModel
class HomeTeacherViewModel @Inject constructor(
    authService: AuthService
) : BaseHomeViewModel(authService) {
}