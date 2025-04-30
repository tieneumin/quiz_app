package com.example.quizapp.data.repo

import com.example.quizapp.data.model.Quiz
import kotlinx.coroutines.flow.Flow

class QuizRepoFirestoreImpl() : QuizRepo {
    override fun getQuizzes(): Flow<List<Quiz>> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuizById(id: String): Quiz? {
        TODO("Not yet implemented")
    }

    override suspend fun addQuiz(quiz: Quiz) {
        TODO("Not yet implemented")
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteQuiz(id: String) {
        TODO("Not yet implemented")
    }
}