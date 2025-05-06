package com.example.quizapp.ui.home.teacher

import com.example.quizapp.core.service.AuthService
import com.example.quizapp.data.repo.QuizRepo
import com.example.quizapp.data.repo.UserRepo
import com.example.quizapp.ui.home.base.BaseHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// handles Quizzes/TeacherProfileFragment logic; getQuizzes<Flow>/deleteQuiz
@HiltViewModel
class TeacherHomeViewModel @Inject constructor(
    private val repo: QuizRepo,
    userRepo: UserRepo,
    authService: AuthService
) : BaseHomeViewModel(userRepo, authService) {
//    private val _state = MutableStateFlow(HomeState())
//    val state = _state.asStateFlow()
//
//    init {
//        getQuizzes()
//    }
//
//    private fun getQuizzes() {
//        try {
//            viewModelScope.launch(Dispatchers.IO) {
//                repo.getQuizzes().collect { quizzes ->
//                    _state.update { it.copy(quizzes = quizzes, isLoading = false) }
//                }
//            }
//        } catch (e: Exception) {
//            _state.update { it.copy(errorMessage = e.message, isLoading = false) }
//        }
//    }
//
//    private fun deleteQuiz(id: String) {
//        try {
//            viewModelScope.launch(Dispatchers.IO) {
//                repo.deleteQuiz(id)
//                _state.update { it.copy(successMessage = "Quiz deleted successfully") }
//            }
//        } catch (e: Exception) {
//            _state.update { it.copy(errorMessage = e.message) }
//        }
//    }
}

//data class HomeState(
//    val quizzes: List<Quiz>? = null,
//    val isLoading: Boolean = true,
//    val successMessage: String? = null,
//    val errorMessage: String? = null,
//)