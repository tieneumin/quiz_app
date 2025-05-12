package com.example.quizapp.ui.home.student

import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val repo: QuizRepo,
) : BaseViewModel() {
    suspend fun checkForQuiz(id: String): Quiz? {
        return withContext(Dispatchers.IO) {
            errorHandler {
                require(id.isNotEmpty()) { "Quiz ID is required" }
                val quiz = repo.getQuizById(id)
                if (quiz == null) throw Exception("Quiz not found")
                quiz
            }
        }
    }
}