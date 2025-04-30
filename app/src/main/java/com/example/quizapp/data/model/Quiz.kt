package com.example.quizapp.data.model

data class Quiz(
    val id: String? = null, // Firebase id
    val quizId: Int = -1, // 4-6 digit
    val title: String = "",
    val timePerQuestion: Int = -1,
    val questions: List<Question> = emptyList(),
    val creator: String,
)

