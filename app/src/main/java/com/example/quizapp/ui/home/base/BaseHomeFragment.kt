package com.example.quizapp.ui.home.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseHomeFragment : Fragment() {
    protected lateinit var binding: FragmentHomeBinding
    abstract val fragments: List<Fragment>
    abstract val tabTexts: List<String>
    abstract val tabIcons: List<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            viewPager.adapter = TabAdapter(fragments, this@BaseHomeFragment)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTexts[position]
                tab.icon = ContextCompat.getDrawable(requireContext(), tabIcons[position])
            }.attach()
        }
    }
}