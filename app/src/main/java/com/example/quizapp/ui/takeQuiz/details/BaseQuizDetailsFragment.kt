package com.example.quizapp.ui.takeQuiz.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizDetailsBinding
import com.example.quizapp.ui.base.BaseFragment
import com.example.quizapp.ui.takeQuiz.BaseTakeQuizViewModel
import kotlinx.coroutines.launch

abstract class BaseQuizDetailsFragment : BaseFragment() {
    protected lateinit var binding: FragmentQuizDetailsBinding
    abstract override val viewModel: BaseTakeQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.quiz.collect {
                binding.tvQuizId.text = getString(R.string.id, it.id)
                binding.tvTitle.text = getString(R.string.title, it.title)
            }
        }
    }
}