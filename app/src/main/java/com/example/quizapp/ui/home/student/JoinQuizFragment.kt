package com.example.quizapp.ui.home.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentQuizJoinBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinQuizFragment : BaseFragment() { // prevents duplicate success/error observers
    private lateinit var binding: FragmentQuizJoinBinding
    override val viewModel: StudentHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.run {
            btnJoin.setOnClickListener {
                val quizId = etQuizId.text.toString().trim()
                if (quizId.isEmpty()) {
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    val quiz = viewModel.getQuizById(quizId)
                    if (quiz != null) {
                        Toast.makeText(requireContext(), "Quiz found!", Toast.LENGTH_SHORT).show()
                        val action = StudentHomeFragmentDirections.actionStudentHomeFragmentToTakeQuizFragment(quizId)
                        findNavController().navigate(action)
                    }else{
                        Toast.makeText(requireContext(), "Quiz Not found!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
