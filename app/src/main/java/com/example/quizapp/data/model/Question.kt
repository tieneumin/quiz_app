package com.example.quizapp.data.model

data class Question(
    val id: String? = null,
    val text: String = "",
    val options: List<String> = emptyList()
)