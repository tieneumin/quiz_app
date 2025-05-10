package com.example.quizapp.ui.home.student

import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// handles QuizJoin/StudentProfileFragment logic; getQuizById (verify quiz exists)
@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val repo: QuizRepo,
    authService: AuthService
) : BaseHomeViewModel(authService) {

    suspend fun getQuizById(id: String): Quiz? {
        return withContext(Dispatchers.IO) {
            errorHandler {
                repo.getQuizById(id)
            }
        }
    }
}