package com.example.quizapp.ui.home.teacher

import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// getQuizzes flow, delete
@HiltViewModel
class HomeTeacherViewModel @Inject constructor(
    private val repo: QuizRepo,
    private val authService: AuthService
) : BaseHomeViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getQuizzes()
    }

    private fun getQuizzes() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getQuizzes().collect { quizzes ->
                    _state.update { it.copy(quizzes = quizzes, isLoading = false) }
                }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message, isLoading = false) }
        }
    }

    private fun deleteQuiz(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.deleteQuiz(id)
                _state.update { it.copy(successMessage = "Quiz deleted successfully") }
            }
        } catch (e: Exception) {
            _state.update { it.copy(errorMessage = e.message) }
        }
    }
}

data class HomeState(
    val quizzes: List<Quiz>? = null,
    val isLoading: Boolean = true,
    val successMessage: String? = null,
    val errorMessage: String? = null,
)