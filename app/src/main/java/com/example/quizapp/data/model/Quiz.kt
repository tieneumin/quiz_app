package com.example.quizapp.data.model

data class Quiz(
    val id: String? = null,
    val quizId: Int = -1,
    val title: String = "",
    val secondsPerQuestion: Int = 30,
    val questions: List<Question> = emptyList(),
    val creator: String = "",
)