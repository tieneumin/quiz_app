package com.example.quizapp.ui.home.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuizzesBinding

// logic handled by HomeTeacherViewModel
class QuizzesFragment : Fragment() {
    private lateinit var binding: FragmentQuizzesBinding
    private lateinit var adapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupAdapter()
    }

    private fun setupUI(){
        binding.btnAdd.setOnClickListener {
            val action = HomeTeacherFragmentDirections.actionHomeTeacherFragmentToAddQuizFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupAdapter() {
        adapter = QuizAdapter(emptyList())
        binding.rvQuizzes.adapter = adapter

        adapter.listener = object : QuizAdapter.Listener {
            override fun onClickQuiz(id: String) {
//                val action = HomeTeacherFragmentDirections.actionHomeTeacherFragmentToQuizFragment(id)
//                findNavController().navigate(action)
            }

            override fun onLongClickQuiz(id: String) {
                return
            }

        }
    }


}