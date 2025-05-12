package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentJoinQuizBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinQuizFragment : BaseFragment() {
    private lateinit var binding: FragmentJoinQuizBinding
    override val viewModel: StudentHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.btnJoin.setOnClickListener {
            val quizId = binding.etQuizId.text.toString().trim()
            lifecycleScope.launch {
                viewModel.checkForQuiz(quizId)?.let {
                    val action = StudentHomeFragmentDirections.actionStudentHomeToQuizStartDetails(quizId)
                    findNavController().navigate(action)
                }
            }
        }
    }
}