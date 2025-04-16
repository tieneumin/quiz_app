package com.example.quizapp.data.repo

import com.example.quizapp.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepo {
    fun getQuizzes(): Flow<List<Quiz>>
    suspend fun getQuizById(id: String): Quiz?
    suspend fun addQuiz(quiz: Quiz)
    suspend fun updateQuiz(quiz: Quiz)
    suspend fun deleteQuiz(id: String)
}