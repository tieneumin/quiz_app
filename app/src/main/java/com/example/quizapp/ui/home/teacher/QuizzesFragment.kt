package com.example.quizapp.ui.home.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuizzesBinding
import dagger.hilt.android.AndroidEntryPoint

// logic handled by HomeTeacherViewModel
@AndroidEntryPoint
class QuizzesFragment : Fragment() { // prevents multiple success/error observers
    private lateinit var binding: FragmentQuizzesBinding
    private lateinit var adapter: QuizAdapter
    private val viewModel: HomeTeacherViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            val action = HomeTeacherFragmentDirections.actionHomeTeacherToAddQuiz()
            findNavController().navigate(action)
        }
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = QuizAdapter(emptyList())
        binding.rvQuizzes.adapter = adapter
        adapter.listener = object : QuizAdapter.Listener {
            override fun onClickQuiz(id: String) {
//                val action = HomeTeacherFragmentDirections.actionHomeTeacherToEditQuiz(id)
//                findNavController().navigate(action)
            }
        }
    }
}