package com.example.quizapp.ui.saveQuiz.add

import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModel @Inject constructor(
    private val repo: QuizRepo,
    private val authService: AuthService
) : BaseManageQuizViewModel() {
    private val _importedQuestions = MutableStateFlow<List<Question>>(emptyList())
    val importedQuestions: StateFlow<List<Question>> = _importedQuestions

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
                if (row.size < 7) throw IllegalArgumentException("Insufficient columns")
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
                val quiz = Quiz(
                    title = title,
                    quizId = quizId,
                    secondsPerQuestion = secondsPerQuestion,
                    questions = importedQuestions.value,
                    creator = authService.getUid()!!
                )
                repo.addQuiz(quiz)
                _success.emit("Quiz saved successfully")
            }
        }
    }
}