package com.example.quizapp.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.quizapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

// '.' BaseHomeFragment has TabLayout, BaseHomeViewModel handles ProfileFragment tab's logic
@AndroidEntryPoint
class ProfileFragment : Fragment() { // prevents multiple success/error observers
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: BaseHomeViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            Glide.with(ivPhoto).load(viewModel.getUserPhoto()).into(ivPhoto)
            btnLogout.setOnClickListener {
                viewModel.logout()
                (parentFragment as LogoutHandler).navigateToLogin()
            }
        }
    }

    interface LogoutHandler {
        fun navigateToLogin()
    }
}

