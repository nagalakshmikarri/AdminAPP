package com.example.amplifieradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.example.amplifieradmin.databinding.ActivityReferBusinessBinding
import com.example.amplifieradmin.databinding.ActivityTopBusinessBinding
import com.example.amplifieradmin.ui.main.Adapter.ReferBusinessAdapter
import com.example.amplifieradmin.ui.main.Adapter.TopBusinessAdapter
import com.google.android.material.tabs.TabLayout

class TopBusinessActivity : AppCompatActivity() {
    private var _binding: ActivityTopBusinessBinding? = null
    private val binding get() = _binding!!
    private var indicatorWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTopBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupClicks()
    }

    private fun setupClicks() {
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUI() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All Business"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Priority List"))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        binding.viewPager.isSaveEnabled = false
        val adapter = TopBusinessAdapter(supportFragmentManager)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        //Determine indicator width at runtime
        //Determine indicator width at runtime
        binding.tabLayout.post(Runnable {
            indicatorWidth = binding.tabLayout.width / binding.tabLayout.tabCount

            //Assign new width
            val indicatorParams = binding.indicator.layoutParams as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            binding.indicator.layoutParams = indicatorParams
        })


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager!!.currentItem = tab.position


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {
                val params = binding.indicator.layoutParams as FrameLayout.LayoutParams
                val translationOffset = (positionOffset + i) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                binding.indicator.layoutParams = params
            }

            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
        })

    }
}