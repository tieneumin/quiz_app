package com.example.quizapp.ui.home.base

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepo: UserRepo,
    authService: AuthService
) : BaseHomeViewModel(userRepo, authService)