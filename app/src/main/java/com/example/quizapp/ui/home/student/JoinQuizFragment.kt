package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizapp.databinding.FragmentJoinQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinQuizFragment : Fragment() { // prevents duplicate success/error observers
    private lateinit var binding: FragmentJoinQuizBinding
    private val viewModel: StudentHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
}
