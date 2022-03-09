package com.rigelramadhan.dicodingbfaasubmission.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rigelramadhan.dicodingbfaasubmission.view.FollowerFragment
import com.rigelramadhan.dicodingbfaasubmission.view.FollowingFragment
import com.rigelramadhan.dicodingbfaasubmission.view.RepoFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RepoFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = FollowerFragment()
        }

        return fragment as Fragment
    }
}