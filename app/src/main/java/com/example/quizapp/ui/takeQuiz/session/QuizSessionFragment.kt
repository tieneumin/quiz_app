package com.example.quizapp.ui.takeQuiz.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizSessionBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizSessionFragment : BaseFragment() {
    private lateinit var binding: FragmentQuizSessionBinding
    override val viewModel: QuizSessionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizSessionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.run {
            btnNext.setOnClickListener {
                val selectedIndex = when (rgOptions.checkedRadioButtonId) {
                    rbOption0.id -> 0
                    rbOption1.id -> 1
                    rbOption2.id -> 2
                    rbOption3.id -> 3
                    else -> -1
                }
                if (selectedIndex != -1) {
                    viewModel.checkAnswer(selectedIndex)
                } else {
                    showErrorSnackbar("Please select an answer")
                }
            }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        setupQuizObserver()
        setupCurrentQuestionObserver()
        setupTimerObserver()
        setupQuizCompleteObserver()
    }

    private fun setupQuizObserver() {
        lifecycleScope.launch {
            viewModel.quiz.collect {
                binding.llSession.isVisible = it.questions.isNotEmpty()
                if (it.questions.isNotEmpty()) viewModel.startQuiz()
            }
        }
    }

    private fun setupCurrentQuestionObserver() {
        lifecycleScope.launch {
            viewModel.currentQuestion.collect { (index, question, total) ->
                if (question.answerIndex != -1) {
                    binding.run {
                        rgOptions.clearCheck()
                        tvQuestionCount.text = getString(
                            R.string.question_x_of_y,
                            (index + 1).toString(),
                            total.toString()
                        )
                        tvQuestion.text = question.questionText
                        rbOption0.text = question.options[0]
                        rbOption1.text = question.options[1]
                        rbOption2.text = question.options[2]
                        rbOption3.text = question.options[3]
                    }
                }
            }
        }
    }

    private fun setupTimerObserver() {
        lifecycleScope.launch {
            viewModel.timer.collect {
                binding.tvTimer.text = it.toString()
            }
        }
    }

    private fun setupQuizCompleteObserver() {
        lifecycleScope.launch {
            viewModel.quizComplete.collect { (quizId, score) ->
                val action = QuizSessionFragmentDirections.actionQuizSessionToQuizEndDetails(
                    quizId, score
                )
                findNavController().navigate(action)
            }
        }
    }
}