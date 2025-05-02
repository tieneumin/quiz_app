package com.example.quizapp.ui.home.teacher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.data.model.Quiz
import com.example.quizapp.databinding.ItemQuizBinding

class QuizAdapter(
    private var quizzes: List<Quiz>,
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuizBinding.inflate(inflater, parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz)
    }

    override fun getItemCount() = quizzes.size

    fun setQuizzes(quizzes: List<Quiz>) {
        this.quizzes = quizzes
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: ItemQuizBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvTitle.text = quiz.title
                tvQuizId.text = quiz.quizId.toString()
                mcvQuiz.setOnClickListener { listener?.onClickQuiz(quiz.id!!) }
            }
        }
    }

    interface Listener {
        fun onClickQuiz(id: String)
    }
}