package com.example.quizapp.ui.saveQuiz.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.databinding.FragmentManageQuizBinding
import com.example.quizapp.ui.base.BaseFragment

abstract class BaseManageQuizFragment : BaseFragment() {
    protected lateinit var binding: FragmentManageQuizBinding
    abstract override val viewModel: BaseManageQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
}