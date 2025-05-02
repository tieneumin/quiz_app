package com.example.quizapp.ui.home.base

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.ui.base.BaseViewModel

// ProfileFragment logic - user name/role updated
// success State/SharedFlow e.g. quiz deleted/joined
abstract class BaseHomeViewModel(
    protected val authService: AuthService
) : BaseViewModel() {
    fun getUserPhoto() = authService.getUserPhoto()
    fun logout() = authService.logout()
}