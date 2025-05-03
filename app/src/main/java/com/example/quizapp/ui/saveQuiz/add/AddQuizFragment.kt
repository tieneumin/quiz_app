package com.example.quizapp.ui.saveQuiz.add

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quizapp.R
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
    override val title: String = "Add New Quiz"
    override val quiz: Quiz? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiComponents()
    }

    override fun setupUiComponents() {
        binding.run {
            tvManageTitle.text = title

            btnSaveQuiz.setOnClickListener {
                val title = etQuizName.text.toString().trim()
                val quizId = etQuizId.text.toString().trim().toIntOrNull()
                val secondsPerQuestion = etTpq.text.toString().trim().toIntOrNull()

                if (quizId == null || secondsPerQuestion == null || title.isBlank()) {
                    return@setOnClickListener
                }
                onSaveClicked(title, quizId, secondsPerQuestion)
            }

            btnImport.setOnClickListener {
                csvFilePickerLauncher.launch("*/*")
            }
        }
    }

    private val csvFilePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                importQuestionsFromCsvUri(uri)
            } else {
                showError("No file selected")
            }
        }

    private fun importQuestionsFromCsvUri(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val csvReader = CSVReader(reader)
            val rows = csvReader.readAll()
            val questions = viewModel.parseQuestionsFromCsv(rows)
            viewModel.importQuestions(questions)
        } catch (e: Exception) {
            showError(e.message.toString())
        }
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

    private fun onSaveClicked(title: String, quizId: Int, secondsPerQuestion: Int) {
        viewModel.saveQuiz(title, quizId, secondsPerQuestion)
    }
}