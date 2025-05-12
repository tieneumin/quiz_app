package com.example.quizapp.data.repo

import com.example.quizapp.data.model.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepoFirestoreImpl(
    private val db: FirebaseFirestore = Firebase.firestore,
) : QuizRepo {
    override fun getQuizzes(): Flow<List<Quiz>> = callbackFlow {
        val listener = db.collection("quizzes").addSnapshotListener { value, error ->
            if (error != null) {
                trySend(emptyList())
                return@addSnapshotListener
            }
            val quizzes = mutableListOf<Quiz>()
            value?.documents?.forEach { doc ->
                doc.toObject(Quiz::class.java)?.let { quiz ->
                    quizzes.add(quiz.copy(id = doc.id))
                }
            }
            trySend(quizzes)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun addQuiz(quiz: Quiz) {
        val id = generateUniqueQuizId()
        val docRef = db.collection("quizzes").document(id)
        docRef.set(quiz.copy(id = id)).await()
    }

    override suspend fun generateUniqueQuizId(): String {
        while (true) {
            val id = (1000..9999).random().toString()
            val quizExists = db.collection("quizzes").document(id).get().await().exists()
            if (!quizExists) return id
        }
    }

    override suspend fun getQuizById(id: String): Quiz? {
        val snapshot = db.collection("quizzes").document(id).get().await()
        return snapshot.toObject(Quiz::class.java)
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        db.collection("quizzes").document(quiz.id!!).set(quiz).await()
    }

    override suspend fun deleteQuiz(id: String) {
        db.collection("quizzes").document(id).delete().await()
    }
}