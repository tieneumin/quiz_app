package com.example.quizapp.ui.takeQuiz.details.end

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.ui.takeQuiz.details.BaseQuizDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizEndDetailsFragment : BaseQuizDetailsFragment() {
    override val viewModel: QuizEndDetailsViewModel by viewModels()
    private val args: QuizEndDetailsFragmentArgs by navArgs()

    override fun setupUiComponents() {
        binding.run {
            tvDetails.text = getString(R.string.results)
            btnDetails.text = getString(R.string.end_quiz)
            btnDetails.setOnClickListener {
                findNavController().popBackStack()
                findNavController().popBackStack()
            }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                quiz.id?.let {
                    viewModel.savePercentScoreAndCalculateRank()
                    binding.tvDetails1.text = getString(
                        R.string.score, args.score.toString(), quiz.questions.size.toString()
                    )
                }
            }
        }
        lifecycleScope.launch {
            viewModel.rank.collect {
                binding.tvDetails2.text = it
            }
        }
    }
}