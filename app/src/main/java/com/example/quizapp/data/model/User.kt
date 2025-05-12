package com.example.quizapp.data.model

data class User(
    val email: String = "",
    val role: String = "student",
) {
    fun toHashMap(): Map<String, Any> {
        return mapOf(
            "email" to email,
            "role" to role,
        )
    }
}