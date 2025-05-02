package com.example.quizapp.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.FragmentHomeBinding
import com.example.quizapp.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseHomeFragment() : BaseFragment() {
    protected lateinit var binding: FragmentHomeBinding
    protected abstract val fragments: List<Fragment>
    protected abstract val tabTexts: List<String>
    protected abstract val tabIcons: List<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents() {
        binding.run {
            viewPager.adapter = TabAdapter(fragments, this@BaseHomeFragment)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTexts[position]
                tab.icon = ContextCompat.getDrawable(requireContext(), tabIcons[position])
            }.attach()
        }
    }
}