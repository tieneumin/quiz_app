package com.example.quizapp.ui.saveQuiz.add

import androidx.fragment.app.viewModels
import com.example.quizapp.R
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddQuizFragment : BaseManageQuizFragment() {
    override val viewModel: AddQuizViewModel by viewModels()

    override fun setupUiComponents() {
        super.setupUiComponents()
        binding.tvManage.text = getString(R.string.add_quiz)
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
    }
}