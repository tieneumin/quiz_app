package com.example.quizapp.data.model

data class Question(
    val text: String = "",
    val options: List<String> = emptyList(),
    val answer: Int = -1,
)