package com.example.quizapp.ui.saveQuiz.base

import com.example.quizapp.ui.base.BaseViewModel

abstract class BaseManageQuizViewModel: BaseViewModel() {

    abstract fun saveQuiz(title: String, quizId: Int, secondsPerQuestion: Int)
}