package com.example.quizapp.data.repo

import com.example.quizapp.data.model.User

interface UserRepo {
    suspend fun getUserById(id: String): User?
    suspend fun addUser(id: String)
    suspend fun updateUser(user: User)
}