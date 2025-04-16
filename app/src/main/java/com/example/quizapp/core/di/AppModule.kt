package com.example.quizapp.core.di

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.core.service.AuthServiceImpl
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.data.repo.QuizRepoFirestoreImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideQuizRepo(authService: AuthService): QuizRepo {
        return QuizRepoFirestoreImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }
}