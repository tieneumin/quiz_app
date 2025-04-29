package com.example.quizapp.ui.home.student

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// getQuizById; verify quiz exists
@HiltViewModel
class HomeStudentViewModel @Inject constructor(
    authService: AuthService
) : BaseHomeViewModel(authService) {
}