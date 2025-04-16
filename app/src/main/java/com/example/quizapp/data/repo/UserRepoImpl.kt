package com.example.quizapp.data.repo

import com.example.quizapp.data.model.User

class UserRepoImpl : UserRepo {
    override suspend fun getUserById(id: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}