package com.example.quizapp.ui.takeQuiz.session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.takeQuiz.BaseTakeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizSessionViewModel @Inject constructor(
    repo: QuizRepo,
    args: SavedStateHandle
) : BaseTakeQuizViewModel(repo, args) {
    private var totalQuestions = -1
    private var index = 0
    private var timerJob: Job? = null
    private var score: Int = 0

    private val _currentQuestion = MutableStateFlow(Triple(0, Question(), 0))
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()

    private val _quizComplete = MutableSharedFlow<Pair<String, Int>>()
    val quizComplete = _quizComplete.asSharedFlow()

    fun startQuiz() {
        totalQuestions = quiz.value.questions.size
        showQuestion()
    }

    private fun showQuestion() {
        _currentQuestion.update { Triple(index, quiz.value.questions[index], totalQuestions) }
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (i in quiz.value.secondsPerQuestion downTo 1) {
                _timer.emit(i)
                delay(1000)
            }
            _timer.emit(0)
            nextQuestion()
        }
    }

    fun checkAnswer(selectedIndex: Int) {
        val question = currentQuestion.value.second
        val correct = selectedIndex == question.answerIndex
        if (correct) score++
        viewModelScope.launch {
            _success.emit(
                if (correct) "Correct!"
                else "Incorrect! Answer: '${question.options[question.answerIndex]}'"
            )
        }
        nextQuestion()
    }

    private fun nextQuestion() {
        index++
        if (index < quiz.value.questions.size) {
            showQuestion()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        viewModelScope.launch {
            _quizComplete.emit(Pair(id, score))
        }
    }
}