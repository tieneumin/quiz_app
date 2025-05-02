package com.example.quizapp.ui.auth.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
) : BaseViewModel() {
    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                require(email.isNotEmpty() && pass.isNotEmpty()) { "All fields are required" }
                val res = authService.login(email, pass)
                if (res) _success.emit("Password login successful")
            }
        }
    }

    fun loginWithGoogle(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler { authService.loginWithGoogle(context) }?.let {
                if (it) _success.emit("Google login successful")
            }
        }
    }

    suspend fun getUserRole(): String? {
        return withContext(Dispatchers.IO) {
            errorHandler { authService.getUserRole() }
        }
    }
}