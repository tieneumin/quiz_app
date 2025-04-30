package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizapp.databinding.FragmentQuizJoinBinding

// logic handled by HomeStudentViewModel
class QuizJoinFragment : Fragment() { // // prevents multiple success/error observers
    private lateinit var binding: FragmentQuizJoinBinding
    val viewModel: HomeStudentViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizJoinBinding.inflate(inflater, container, false)
        return binding.root
    }
}
