package com.example.quizapp.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.ui.base.BaseFragment

// TabLayout goes here; pass tabs as params to TabLayout so both HomeStudent/TeacherFragment can use
abstract class BaseHomeFragment() : BaseFragment() {
    protected lateinit var binding: FragmentHomeBinding
    abstract override val viewModel: BaseHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.run {
            Glide.with(ivPhoto).load(viewModel.getUserPhoto()).into(ivPhoto)
            btnLogout.setOnClickListener {
                viewModel.logout()
                navigateLogout()
            }
        }
    }

    abstract fun navigateLogout()
}