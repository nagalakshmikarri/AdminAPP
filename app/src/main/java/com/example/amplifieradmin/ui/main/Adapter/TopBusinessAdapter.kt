package com.example.amplifieradmin.ui.main.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.amplifieradmin.fragment.*


class TopBusinessAdapter(
    fragmentManager: FragmentManager,
)  : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllBusinessFragment.newInstance()
            }
            else -> {
                PriorityListFragment.newInstance()

            }
        }
    }


    override fun getCount(): Int {
        return 2
    }

}