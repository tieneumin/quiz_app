package com.example.quizapp.ui.home.teacher

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.FragmentQuizzesBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizzesFragment : BaseFragment() {
    private lateinit var binding: FragmentQuizzesBinding
    private lateinit var adapter: QuizAdapter
    override val viewModel: TeacherHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        setupAdapter()
        binding.btnAdd.setOnClickListener {
            val action = TeacherHomeFragmentDirections.actionTeacherHomeToAddQuiz()
            findNavController().navigate(action)
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.quizzes.collect {
                adapter.setQuizzes(it)
                binding.tvEmpty.isVisible = it.isEmpty()
            }
        }
    }

    private fun setupAdapter() {
        adapter = QuizAdapter(emptyList())
        binding.rvQuizzes.adapter = adapter
        binding.rvQuizzes.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = object : QuizAdapter.Listener {
            override fun onClickQuiz(id: String) {
                val action = TeacherHomeFragmentDirections.actionTeacherHomeToEditQuiz(id)
                findNavController().navigate(action)
            }

            override fun onClickDelete(id: String) {
                showDeleteQuizDialog { viewModel.deleteQuiz(id) }
            }
        }
    }

    private fun showDeleteQuizDialog(deleteMethod: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Delete quiz?")
            .setMessage("This action cannot be undone.")
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Delete") { dialog, _ ->
                deleteMethod()
                dialog.dismiss()
            }.show()
    }
}