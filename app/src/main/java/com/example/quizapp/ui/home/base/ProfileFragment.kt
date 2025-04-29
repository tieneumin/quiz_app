package com.example.quizapp.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizapp.databinding.FragmentProfileBinding

// '.' BaseHomeFragment has TabLayout, BaseHomeViewModel handles ProfileFragment tab's logic
class ProfileFragment : Fragment() { // prevents multiple success/error observers
    private lateinit var binding: FragmentProfileBinding
    val viewModel: BaseHomeViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}