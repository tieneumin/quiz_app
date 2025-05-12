package com.example.quizapp.data.repo

import com.example.quizapp.data.model.User

interface UserRepo {
    suspend fun addUser(uid: String, email: String)
    suspend fun getUserById(uid: String): User?
    suspend fun updateUser(uid: String, user: User)
}