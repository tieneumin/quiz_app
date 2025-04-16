package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.FragmentQuizJoinBinding

// logic handled by HomeStudentViewModel
class QuizJoinFragment : Fragment() {
    private lateinit var binding: FragmentQuizJoinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizJoinBinding.inflate(inflater, container, false)
        return binding.root
    }
}
