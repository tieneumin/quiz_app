package com.example.quizapp.ui.saveQuiz.edit

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.R
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditQuizFragment : BaseManageQuizFragment() {
    override val viewModel: EditQuizViewModel by viewModels()

    override fun setupUiComponents() {
        super.setupUiComponents()
        binding.tvManage.text = getString(R.string.edit_quiz)
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.quiz.collect {
                binding.etTitle.setText(it.title)
                binding.etTime.setText(it.secondsPerQuestion.toString())
            }
        }
    }
}