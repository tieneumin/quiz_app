package com.example.quizapp.ui.home.base

import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.User
import com.example.quizapp.data.repo.UserRepo
import com.example.quizapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseHomeViewModel @Inject constructor(
    protected val userRepo: UserRepo,
    protected val authService: AuthService
) : BaseViewModel() {
    private var uid = ""
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        getUserById()
    }

    private fun getUserById() {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                authService.getUid()?.let {
                    uid = it
                    userRepo.getUserById(it)
                }
            }?.let { user ->
                _user.update { user }
            }
        }
    }

    fun getUserPhoto() = authService.getUserPhoto()
    fun updateUser(checkedId: Int) {
        val role = if (checkedId == R.id.rbStudent) "student" else "teacher"
        if (role != user.value.role) {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler {
                    userRepo.updateUser(uid, user.value.copy(role = role))
                    _success.emit("Role updated; please log in again")
                    logout()
                }
            }
        }
    }

    fun logout() = authService.logout()
}