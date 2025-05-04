package com.example.quizapp.ui.saveQuiz.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuizViewModel @Inject constructor(
    private val repo: QuizRepo,
    args: SavedStateHandle
) : BaseManageQuizViewModel() {
    val id = args.get<String>("id") ?: ""

    private val _quiz = MutableStateFlow(Quiz(title = "", quizId = -1, secondsPerQuestion = -1,questions= emptyList()))
    val quiz = _quiz.asStateFlow()

    private val _importedQuestions = MutableStateFlow<List<Question>>(emptyList())
    val importedQuestions: StateFlow<List<Question>> = _importedQuestions

    init {
        getQuizById()
    }

    private fun getQuizById(){
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                repo.getQuizById(id)
            }?.let{
                _quiz.update { it }
            }
        }
    }

    fun importQuestions(questions: List<Question>) {
        _importedQuestions.value = questions
        viewModelScope.launch {
            _success.emit("Imported ${questions.size} questions successfully")
        }
    }

    fun parseQuestionsFromCsv(rows: List<Array<String>>): List<Question> {
        val questions = mutableListOf<Question>()
        val errors = mutableListOf<String>()

        for ((index, row) in rows.withIndex()) {
            if (index == 0) continue
            try {
                if (row.size < 7) {
                    throw IllegalArgumentException("Insufficient columns")
                    break;
                }
                val text = row[1].trim()
                val options = row.slice(2..5).map { it.trim() }
                val answerIndex = row[6].trim().toIntOrNull()?.minus(1)
                    ?: throw IllegalArgumentException("Invalid answer index")

                if (answerIndex !in 0..3) throw IllegalArgumentException("Answer index out of bounds")

                questions.add(Question(text = text, options = options, answer = answerIndex))
            } catch (e: Exception) {
                errors.add("Row ${index + 1}: ${e.message}")
            }
        }

        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                _error.emit("Imported with ${errors.size} error(s):\n" + errors.joinToString("\n"))
            }
        }

        return questions
    }

    override fun saveQuiz(title: String, quizId: Int, secondsPerQuestion: Int) {
        viewModelScope.launch {
            errorHandler {
                val updatedQuiz = quiz.value.copy(
                    title = title,
                    quizId = quizId,
                    secondsPerQuestion = secondsPerQuestion,
                    questions = if (importedQuestions.value.isNotEmpty()) importedQuestions.value else quiz.value.questions
                )
                repo.updateQuiz(updatedQuiz)
                _success.emit("Quiz updated successfully")
            }
        }
    }

}