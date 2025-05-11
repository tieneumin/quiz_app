package com.example.quizapp.ui.takeQuiz.details.end

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.takeQuiz.BaseTakeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizEndDetailsViewModel @Inject constructor(
    repo: QuizRepo,
    args: SavedStateHandle
) : BaseTakeQuizViewModel(repo, args) {
    private val score = args.get<Integer>("score")
    private val _rank = MutableStateFlow("")
    val rank = _rank.asStateFlow()

    fun savePercentScoreAndCalculateRank() {
        score?.let {
            // % prevents ranking issues if # of questions edited
            val percentScore = it.toFloat() / quiz.value.questions.size.toFloat() * 100f
            val updatedScores = quiz.value.percentScores + percentScore
            val sorted = updatedScores.sortedDescending()
            val rank = sorted.indexOf(percentScore) + 1
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler {
                    repo.updateQuiz(quiz.value.copy(percentScores = updatedScores))
                    _rank.emit("Rank: #$rank of ${sorted.size}")
                }
            }
        }
    }
}