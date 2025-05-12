package com.example.quizapp.ui.takeQuiz.details.start

import androidx.lifecycle.SavedStateHandle
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.takeQuiz.BaseTakeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuizStartDetailsViewModel @Inject constructor(
    repo: QuizRepo,
    args: SavedStateHandle
) : BaseTakeQuizViewModel(repo, args) {
    suspend fun checkForQuestions(): Boolean {
        return withContext(Dispatchers.Main) {
            errorHandler {
                require(quiz.value.questions.isNotEmpty()) {
                    "Quiz has no questions; please contact your teacher"
                }
                true
            } == true
        }
    }
}