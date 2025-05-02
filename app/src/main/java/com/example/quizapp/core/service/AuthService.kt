package com.example.quizapp.core.service

import android.content.Context
import android.net.Uri

interface AuthService {
    suspend fun register(email: String, password: String): Boolean
    suspend fun login(email: String, password: String): Boolean
    suspend fun loginWithGoogle(context: Context): Boolean
    fun getUid(): String?
    suspend fun getUserRole(): String?
    fun getUserPhoto(): Uri?
    fun logout()
}