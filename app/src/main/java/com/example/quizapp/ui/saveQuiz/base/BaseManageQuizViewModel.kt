package com.example.quizapp.ui.saveQuiz.base

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseManageQuizViewModel(
    protected val repo: QuizRepo
) : BaseViewModel() {
    protected val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    protected val _navigate = MutableSharedFlow<Unit>()
    val navigate = _navigate.asSharedFlow()

    fun importQuestionsFromCsv(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                parseQuestionsFromCsv(context, uri)
            }?.let { parsedQs ->
                _questions.update { parsedQs }
                _success.emit("Imported ${parsedQs.size} questions successfully")
            }
        }
    }

    private fun parseQuestionsFromCsv(context: Context, uri: Uri): List<Question> {
        context.contentResolver.openInputStream(uri)?.bufferedReader()?.useLines { rows ->
            return rows.drop(1).mapIndexed { i, row ->
                val columns = row.split(",")
                if (columns.size != 6) throw Exception("Line ${i + 2} does not have 6 values")

                val questionText = columns[0].trim()
                val options = columns.slice(1..4).map { it.trim() }
                val answerIndex = columns[5].trim().toIntOrNull()
                    ?: throw Exception("Line ${i + 2}: Answer index is not an integer")

                if (answerIndex !in 0..3) throw Exception("Line ${i + 2}: Answer index is out of range (0-3)")

                Question(questionText, options, answerIndex)
            }.toList()
        } ?: throw Exception("Unable to open file")
    }

    abstract fun saveQuiz(title: String, secondsPerQuestion: Int?)
    protected fun prepareQuiz(title: String, secondsPerQuestion: Int): Quiz {
        return Quiz(
            title = title,
            secondsPerQuestion = secondsPerQuestion,
            questions = questions.value
        )
    }
}