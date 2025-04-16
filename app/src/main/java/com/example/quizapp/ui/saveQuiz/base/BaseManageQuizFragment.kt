package com.example.quizapp.ui.saveQuiz.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.FragmentManageQuizBinding

abstract class BaseManageQuizFragment : Fragment() {
    protected lateinit var binding: FragmentManageQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
}