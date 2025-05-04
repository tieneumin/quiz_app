package com.example.quizapp.data.repo

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class QuizRepoFirestoreImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
) : QuizRepo {
    override fun getQuizzes(): Flow<List<Quiz>> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuizById(id: String): Quiz? {
        TODO("Not yet implemented")
    }

    override suspend fun addQuiz(quiz: Quiz) {
        val docRef = db.collection("quizzes").document()
        val quizWithId = quiz.copy(id = docRef.id)
        docRef.set(quizWithId).await()
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        val docRef = db.collection("quizzes").document(quiz.id!!)
        docRef.set(quiz).await()
    }

    override suspend fun deleteQuiz(id: String) {
        TODO("Not yet implemented")
    }
}