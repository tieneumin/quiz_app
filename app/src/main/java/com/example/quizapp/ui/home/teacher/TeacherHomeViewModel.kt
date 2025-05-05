package com.example.quizapp.ui.home.teacher

import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.data.repo.UserRepo
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// handles Quizzes/TeacherProfileFragment logic; getQuizzes<Flow>/deleteQuiz
@HiltViewModel
class TeacherHomeViewModel @Inject constructor(
    private val repo: QuizRepo,
    userRepo: UserRepo,
    authService: AuthService
) : BaseHomeViewModel(userRepo, authService) {
    private val _quizzes = MutableStateFlow(emptyList<Quiz>())
    val quizzes = _quizzes.asStateFlow()

    init {
        getQuizzes()
    }

    private fun getQuizzes() {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                repo.getQuizzes().collect { items ->
                    _quizzes.update {
                        items.filter { it.creator == authService.getUid() }
                    }
                }
            }
        }
    }

    fun deleteQuiz(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                repo.deleteQuiz(id)
                _success.emit("Quiz deleted")
            }
        }
    }
}