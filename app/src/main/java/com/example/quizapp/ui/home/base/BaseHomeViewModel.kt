package com.example.quizapp.ui.home.base

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseHomeViewModel @Inject constructor(
    protected val authService: AuthService
) : BaseViewModel() {
    fun getUserPhoto() = authService.getUserPhoto()
    fun logout() = authService.logout()
}