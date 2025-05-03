package com.example.quizapp.data.model

data class Question(
    val id: String? = null,
    val questionNumber: Int = -1,
    val text: String = "",
    val options: List<String> = emptyList(),
    val answer: Int = -1,
)