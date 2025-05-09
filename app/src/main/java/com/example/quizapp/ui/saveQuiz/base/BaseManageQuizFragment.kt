package com.example.quizapp.ui.saveQuiz.base

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentManageQuizBinding
import com.example.quizapp.ui.base.BaseFragment
import kotlinx.coroutines.launch

abstract class BaseManageQuizFragment : BaseFragment() {
    protected lateinit var binding: FragmentManageQuizBinding
    abstract override val viewModel: BaseManageQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.run {
            btnImport.setOnClickListener { csvFilePickerLauncher.launch("*/*") }
            btnSave.setOnClickListener {
                val title = etTitle.text.toString()
                val secondsPerQuestion = etTime.text.toString().toIntOrNull()
                viewModel.saveQuiz(title, secondsPerQuestion)
            }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.navigate.collect {
                findNavController().popBackStack()
            }
        }
    }

    private val csvFilePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { nullableUri ->
            nullableUri?.let { uri ->
                val fileName = getFileNameFromUri(uri)
                if (fileName?.endsWith(".csv") == true) {
                    viewModel.importQuestionsFromCsv(requireContext(), uri)
                } else {
                    showErrorSnackbar("Please select a valid CSV file")
                }
            }
        }

    private fun getFileNameFromUri(uri: Uri): String? {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                return it.getString(nameIndex)
            }
        }
        return null
    }
}