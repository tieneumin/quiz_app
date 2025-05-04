package com.example.quizapp.ui.saveQuiz.add

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.R
import com.example.quizapp.core.log
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.ui.saveQuiz.base.BaseManageQuizFragment
import com.google.android.material.snackbar.Snackbar
import com.opencsv.CSVReader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class AddQuizFragment : BaseManageQuizFragment() {
    override val viewModel: AddQuizViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiComponents()
        setupViewModelObservers()
    }

    override fun setupUiComponents() {
        binding.run {
            tvManageTitle.text = "Add New Quiz"

            btnSaveQuiz.setOnClickListener {
                val title = etQuizName.text.toString().trim()
                val quizId = etQuizId.text.toString().trim().toIntOrNull()
                val secondsPerQuestion = etTpq.text.toString().trim().toIntOrNull()

                if (quizId == null || secondsPerQuestion == null || title.isBlank()) {
                    return@setOnClickListener
                }
                viewModel.saveQuiz(title, quizId, secondsPerQuestion)
            }

            btnImport.setOnClickListener {
                csvFilePickerLauncher.launch("*/*")
            }
        }
    }

    private val csvFilePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val fileName = getFileName(uri)
                if (fileName == null || !fileName.endsWith(".csv", ignoreCase = true)) {
                    showError("Please select a valid CSV file")
                    return@registerForActivityResult
                }

                importQuestionsFromCsvUri(uri)
            } else {
                showError("No file selected")
            }
        }

    private fun getFileName(uri: Uri): String? {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                return it.getString(nameIndex)
            }
        }
        return null
    }

    private fun importQuestionsFromCsvUri(uri: Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val csvReader = CSVReader(reader)
        val rows = csvReader.readAll()
        val questions = viewModel.parseQuestionsFromCsv(rows)
        viewModel.importQuestions(questions)
    }

    private fun showError(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .show()
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.importedQuestions.collect { questions ->
//                (binding.rvQuestions.adapter as QuestionAdapter).submitList(questions)
            }
        }
    }

}