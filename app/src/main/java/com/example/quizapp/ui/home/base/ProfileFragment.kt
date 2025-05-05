package com.example.quizapp.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentProfileBinding
import com.example.quizapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class ProfileFragment() : BaseFragment() {
    protected lateinit var binding: FragmentProfileBinding
    override val viewModel: BaseHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.run {
            Glide.with(ivPhoto).load(viewModel.getUserPhoto()).into(ivPhoto)
            btnUpdate.setOnClickListener {
                viewModel.updateUser(rgRole.checkedRadioButtonId)
            }
            chipLogout.setOnClickListener {
                viewModel.logout()
                (parentFragment as? BaseHomeFragment)?.onLogoutNavigate()
            }
        }
    }

    override fun setupViewModelObservers() {
        super.setupViewModelObservers()
        lifecycleScope.launch {
            viewModel.user.collect {
                binding.tvEmail.text = it.email
                binding.rgRole.check(
                    if (it.role == "student") R.id.rbStudent else R.id.rbTeacher
                )
            }
        }
        lifecycleScope.launch {
            viewModel.success.collect {
                (parentFragment as? BaseHomeFragment)?.onLogoutNavigate()
            }
        }
    }
}