package com.example.quizapp.ui.takeQuiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseTakeQuizViewModel(
    protected val repo: QuizRepo,
    args: SavedStateHandle
) : BaseViewModel() {
    protected val id = args.get<String>("id") ?: ""
    protected val _quiz = MutableStateFlow(Quiz())
    val quiz = _quiz.asStateFlow()

    init {
        getQuizById()
    }

    private fun getQuizById() {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler { repo.getQuizById(id) }?.let { quiz ->
                _quiz.update { quiz }
            }
        }
    }
}