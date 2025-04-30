package com.example.quizapp.data.repo

import com.example.quizapp.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepoFirestoreImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
) : UserRepo {
    override suspend fun addUser(uid: String, email: String) {
        db.collection("users").document(uid).set(User(email = email)).await()
    }

    override suspend fun getUserById(uid: String): User? {
        val snapshot = db.collection("users").document(uid).get().await()
        return snapshot.toObject(User::class.java)
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}