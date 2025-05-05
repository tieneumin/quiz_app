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

@AndroidEntryPoint
class QuizzesFragment : Fragment() { // prevents duplicate success/error observers
    private lateinit var binding: FragmentQuizzesBinding
    private lateinit var adapter: QuizAdapter
    private val viewModel: TeacherHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        binding.btnAdd.setOnClickListener {
            val action = TeacherHomeFragmentDirections.actionTeacherHomeToAddQuiz()
            findNavController().navigate(action)
        }
    }

    private fun setupAdapter() {
        adapter = QuizAdapter(emptyList())
        binding.rvQuizzes.adapter = adapter
        adapter.listener = object : QuizAdapter.Listener {
            override fun onClickQuiz(id: String) {
//                val action = TeacherHomeFragmentDirections.actionTeacherHomeToEditQuiz(id)
//                findNavController().navigate(action)
            }
        }
    }
}