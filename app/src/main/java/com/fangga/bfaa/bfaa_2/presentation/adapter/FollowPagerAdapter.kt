package com.fangga.bfaa.bfaa_2.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fangga.bfaa.bfaa_2.presentation.ui.follower.FollowerFragment
import com.fangga.bfaa.bfaa_2.presentation.ui.following.FollowingFragment
import com.fangga.bfaa.bfaa_2.utils.Constant.TAB_TITLES

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowerFragment.getInstance(username)
            1 -> fragment = FollowingFragment.getInstance(username)
        }
        return fragment as Fragment
    }
}