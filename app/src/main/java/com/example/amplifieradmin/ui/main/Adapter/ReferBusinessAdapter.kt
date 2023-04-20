package com.example.amplifieradmin.ui.main.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.amplifieradmin.fragment.BlockListFragment
import com.example.amplifieradmin.fragment.ConfirmListFragment
import com.example.amplifieradmin.fragment.ReferBusinessListFragment


class ReferBusinessAdapter(
    fragmentManager: FragmentManager,
)  : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ReferBusinessListFragment.newInstance()
                         }
            1 -> {
                ConfirmListFragment.newInstance()

            }

            else -> {
                BlockListFragment.newInstance()

            }
        }
    }


    override fun getCount(): Int {
        return 3
    }

}