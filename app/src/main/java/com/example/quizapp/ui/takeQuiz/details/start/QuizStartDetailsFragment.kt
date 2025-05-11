package com.example.quizapp.ui.takeQuiz.details.start

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.ui.takeQuiz.details.BaseQuizDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizStartDetailsFragment() : BaseQuizDetailsFragment() {
    override val viewModel: QuizStartDetailsViewModel by viewModels()
    private val args: QuizStartDetailsFragmentArgs by navArgs()

    override fun setupUiComponents() {
        binding.run {
            tvDetails.text = getString(R.string.quiz_details)
            btnDetails.text = getString(R.string.start_quiz)
            btnDetails.setOnClickListener {
                lifecycleScope.launch {
                    if (viewModel.checkForQuestions()) {
                        val action = QuizStartDetailsFragmentDirections
                            .actionQuizStartDetailsToQuizSession(args.id)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.quiz.collect {
                binding.tvDetails1.text = getString(
                    R.string.questions, it.questions.size.toString()
                )
                binding.tvDetails2.text = getString(
                    R.string.time_per_question2, it.secondsPerQuestion.toString()
                )
            }
        }
    }
}