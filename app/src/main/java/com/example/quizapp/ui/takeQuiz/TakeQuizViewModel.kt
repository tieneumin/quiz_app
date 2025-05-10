package com.example.quizapp.ui.takeQuiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.log
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TakeQuizViewModel @Inject constructor(
    private val repo: QuizRepo,
    args: SavedStateHandle
) : BaseViewModel() {
    val id = args.get<String>("id") ?: ""

    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz = _quiz.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _userAnswers = MutableStateFlow<List<Int>>(emptyList())
    val userAnswers = _userAnswers.asStateFlow()

    init {
        getQuizById()
    }

    private fun getQuizById() {
        viewModelScope.launch(Dispatchers.IO){
            errorHandler{
                repo.getQuizById(id)
            }?.let{ q ->
                _quiz.update{ q }
            }
        }
    }

    fun submitAnswer(answerIndex: Int) {
        _userAnswers.update { it + answerIndex }
        log("Answer submitted: $answerIndex")

        _quiz.value?.questions?.let { questions ->
            if (_currentIndex.value < questions.size - 1) {
                _currentIndex.value += 1
            } else {
                _currentIndex.value = questions.size
            }
        }
    }

}