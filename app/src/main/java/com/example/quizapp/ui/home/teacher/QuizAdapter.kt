package com.example.quizapp.ui.home.teacher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
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

    override fun getItemCount() = quizzes.size
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz)
    }

    fun setQuizzes(quizzes: List<Quiz>) {
        this.quizzes = quizzes
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: ItemQuizBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvTitleAndId.text = itemView.context.getString(
                    R.string.title_and_id, quiz.title, quiz.quizId
                )
                tvQuestionCount.text = itemView.context.getString(
                    R.string.question_count, quiz.questions.size
                )
                mcvQuiz.setOnClickListener { listener?.onClickQuiz(quiz.id!!) }
                ivDelete.setOnClickListener { listener?.onClickDelete(quiz.id!!) }
            }
        }
    }

    interface Listener {
        fun onClickQuiz(id: String)
        fun onClickDelete(id: String)
    }
}