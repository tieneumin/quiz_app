package com.example.quizapp.ui.takeQuiz

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.core.log
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.databinding.FragmentTakeQuizBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TakeQuizFragment : BaseFragment() {
    private lateinit var binding: FragmentTakeQuizBinding
    override val viewModel: TakeQuizViewModel by viewModels()
    private var index: Int = 0
    private var score: Int = 0
    private var countdownTimer: CountDownTimer? = null
    private var timerValue: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTakeQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                if (quiz.questions.isEmpty()) {
                    binding.mcvQuestion.visibility = View.GONE
                    binding.tvTimer.text = "Questions Not Found"
                    return@collect
                }
                if (index < quiz.questions.size) {
                    val question = quiz.questions[index]

                    binding.llQuestion.visibility = View.VISIBLE
                    binding.llResult.visibility = View.GONE
                    binding.tvQuestion.text = question.questionText
                    binding.rbOption0.text = question.options[0]
                    binding.rbOption1.text = question.options[1]
                    binding.rbOption2.text = question.options[2]
                    binding.rbOption3.text = question.options[3]

                    timerValue = quiz.secondsPerQuestion * 1000L
                    startTimer(timerValue)

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

                        if (selectedIndex == question.answerIndex) {
                            score++
                        }

                        index++
                        if (index >= quiz.questions.size) {
                            showResult(quiz)
                        }else{
                            setupUiComponents()
                        }
                    }
                }
            }
        }
    }

    private fun startTimer(timeInMillis: Long) {
        countdownTimer?.cancel()
        countdownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvTimer.text = "Time remaining: $secondsRemaining sec"
            }

            override fun onFinish() {
                index++
                lifecycleScope.launch {
                    viewModel.quiz.collect { quiz ->
                        if (index >= quiz.questions.size) {
                            showResult(quiz)
                        }
                    }
                }
            }
        }
        countdownTimer?.start()
    }

    private fun showResult(quiz: Quiz) {
        binding.tvTimer.visibility = View.GONE
        binding.llQuestion.visibility = View.GONE
        binding.llResult.visibility = View.VISIBLE
        binding.tvScore.text = "Your score: $score / ${quiz.questions.size}"
    }
}