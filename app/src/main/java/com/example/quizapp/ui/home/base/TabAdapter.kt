package com.example.quizapp.ui.home.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(
    private val fragments: List<Fragment>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int) = fragments[position]
}