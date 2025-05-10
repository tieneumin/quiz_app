package com.example.quizapp.ui.takeQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.core.log
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.databinding.FragmentTakeQuizBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TakeQuizFragment : BaseFragment() {
    private lateinit var binding: FragmentTakeQuizBinding
    override val viewModel: TakeQuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTakeQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiComponents()
    }

    override fun setupUiComponents() {
        lifecycleScope.launchWhenStarted {
            viewModel.currentIndex.collect {
                viewModel.quiz.value?.let { quiz ->
                    updateUIForQuestionOrResult(quiz)
                }
            }
        }
    }

    private fun updateUIForQuestionOrResult(quiz: Quiz) {
        val index = viewModel.currentIndex.value
        if (index < quiz.questions.size) {
            updateUIForQuestion(quiz, index)
        } else {
            showResult(quiz)
        }
    }

    private fun updateUIForQuestion(quiz: Quiz, index: Int) {
        val question = quiz.questions[index]

        binding.llQuestion.visibility = View.VISIBLE
        binding.llResult.visibility = View.GONE

        binding.tvQuestion.text = question.questionText
        binding.rbOption0.text = question.options[0]
        binding.rbOption1.text = question.options[1]
        binding.rbOption2.text = question.options[2]
        binding.rbOption3.text = question.options[3]

        binding.rgOptions.setOnCheckedChangeListener(null)
        binding.rgOptions.clearCheck()

        binding.rgOptions.setOnCheckedChangeListener { _, checkedId ->
            val selectedIndex = when (checkedId) {
                binding.rbOption0.id -> 0
                binding.rbOption1.id -> 1
                binding.rbOption2.id -> 2
                binding.rbOption3.id -> 3
                else -> return@setOnCheckedChangeListener
            }
            viewModel.submitAnswer(selectedIndex)
        }
    }

    private fun showResult(quiz: Quiz) {
        val currentAnswers = viewModel.userAnswers.value
        var correctCount = 0
        quiz.questions.take(currentAnswers.size).forEachIndexed { i, question ->
            if (question.answerIndex == currentAnswers[i]) {
                correctCount++
            }
        }

        binding.llQuestion.visibility = View.GONE
        binding.llResult.visibility = View.VISIBLE
        binding.tvScore.text = "Your score: $correctCount / ${quiz.questions.size}"

        log("Final Score: $correctCount / ${quiz.questions.size}")
    }
}
