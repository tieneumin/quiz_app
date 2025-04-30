package com.example.quizapp.ui.home.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizapp.databinding.FragmentQuizzesBinding
import com.example.quizapp.ui.base.BaseFragment

// logic handled by HomeTeacherViewModel
class QuizzesFragment : Fragment() { // prevents multiple success/error observers
    private lateinit var binding: FragmentQuizzesBinding
    val viewModel: HomeTeacherViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }
}