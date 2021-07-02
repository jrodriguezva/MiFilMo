package com.jrodriguezva.mifilmo.ui.moviedetail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jrodriguezva.mifilmo.ui.moviedetail.MessageFragment
import com.jrodriguezva.mifilmo.ui.moviedetail.MyMessageFragment

class MessagesViewPagerAdapter(private val movieId: Int, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyMessageFragment.getInstance(movieId)
            else -> MessageFragment.getInstance(movieId)
        }
    }
}