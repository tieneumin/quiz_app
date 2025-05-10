package com.example.quizapp.ui.saveQuiz.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.log
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuizViewModel @Inject constructor(
    repo: QuizRepo,
    args: SavedStateHandle
) : BaseManageQuizViewModel(repo) {
    private val id = args.get<String>("id") ?: ""
    private val _quiz = MutableStateFlow(Quiz())
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

    override fun saveQuiz(title: String, secondsPerQuestion: Int?) {
        viewModelScope.launch {
            errorHandler {
                require(title.isNotEmpty() && secondsPerQuestion != null) { "All fields are required" }
                prepareQuiz(title, secondsPerQuestion).let {
                    repo.updateQuiz(it.copy(id = quiz.value.id, creator = quiz.value.creator))
                    _success.emit("Quiz updated")
                    _navigate.emit(Unit)
                }
            }
        }
    }
}