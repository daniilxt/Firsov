package io.daniilxt.feature.main_screen.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainScreenViewPagerAdapter(fragment: Fragment, private val fragmentsList: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount) {
            throw IllegalStateException("Unsupported position for ${MainScreenViewPagerAdapter::class.simpleName}")
        }
        return fragmentsList[position]
    }
}