package com.example.quizapp.data.model

data class Quiz(
    val id: String? = null, // Firebase id
    val quizId: Int = -1, // 4-6 digit
    val title: String = "",
    val secondsPerQuestion: Int = 30,
    val questions: List<Question> = emptyList(),
    val creator: String,
)