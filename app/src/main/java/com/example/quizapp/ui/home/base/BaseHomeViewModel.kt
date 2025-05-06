package com.example.quizapp.ui.home.base

import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.model.User
import com.example.quizapp.data.repo.UserRepo
import com.example.quizapp.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseHomeViewModel(
    protected val userRepo: UserRepo,
    protected val authService: AuthService
) : BaseViewModel() {
    private var uid = ""
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _logout = MutableSharedFlow<Unit>()
    val logout = _logout.asSharedFlow()

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
                    _logout.emit(Unit)
                }
            }
        }
    }

    fun logout() = authService.logout()
}