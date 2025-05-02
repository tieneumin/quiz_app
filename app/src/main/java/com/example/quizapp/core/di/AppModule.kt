package com.example.quizapp.core.di

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.core.service.AuthServiceImpl
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.data.repo.QuizRepoFirestoreImpl
import dagger.Module
import com.example.quizapp.data.repo.UserRepo
import com.example.quizapp.data.repo.UserRepoFirestoreImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthService(userRepo: UserRepo): AuthService {
        return AuthServiceImpl(userRepo = userRepo)
    }

    @Provides
    @Singleton
    fun provideUserRepo(): UserRepo {
        return UserRepoFirestoreImpl()
    }

    @Provides
    @Singleton
    fun provideQuizRepo(): QuizRepo {
        return QuizRepoFirestoreImpl()
    }
}