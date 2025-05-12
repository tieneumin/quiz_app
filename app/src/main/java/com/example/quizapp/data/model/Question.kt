package com.example.quizapp.data.model

data class Question(
    val questionText: String = "",
    val options: List<String> = emptyList(),
    val answerIndex: Int = -1,
)