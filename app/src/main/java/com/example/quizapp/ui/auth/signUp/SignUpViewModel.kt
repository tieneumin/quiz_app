package com.example.quizapp.ui.auth.signUp

import androidx.lifecycle.viewModelScope
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService
) : BaseViewModel() {
    fun register(email: String, pass: String, confirmPass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                require(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) { "All fields are required" }
                require(pass == confirmPass) { "Entered passwords should match" }
                val res = authService.register(email, pass)
                if (res) _success.emit("Account created")
            }
        }
    }
}