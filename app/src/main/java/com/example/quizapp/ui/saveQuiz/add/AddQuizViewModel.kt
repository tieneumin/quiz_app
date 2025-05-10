package com.example.quizapp.ui.saveQuiz.add

import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModel @Inject constructor(
    repo: QuizRepo,
    private val authService: AuthService
) : BaseManageQuizViewModel(repo) {
    override fun saveQuiz(title: String, secondsPerQuestion: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                require(title.isNotEmpty() && secondsPerQuestion != null) { "All fields are required" }
                prepareQuiz(title, secondsPerQuestion).let { quiz ->
                    authService.getUid()?.let { uid ->
                        repo.addQuiz(quiz.copy(creator = uid))
                        _success.emit("Quiz added")
                        _navigate.emit(Unit)
                    }
                }
            }
        }
    }
}