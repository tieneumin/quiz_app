package com.example.quizapp.ui.home.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.FragmentQuizzesBinding

// logic handled by HomeTeacherViewModel
class QuizzesFragment : Fragment() {
    private lateinit var binding: FragmentQuizzesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }
}