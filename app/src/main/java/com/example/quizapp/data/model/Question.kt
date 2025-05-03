package com.example.quizapp.data.model

data class Question(
    val text: String = "",
    val options: List<String> = emptyList(),// 4 options
    val answer: Int = -1,
)