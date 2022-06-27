package com.application.ebcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.application.ebcom.util.COUNT_VIEW_PAGER_SIZE
import com.application.ebcom.databinding.ActivityMainBinding
import com.application.ebcom.feature.message.presetation.adapter.FragmentMessageStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewPagerMain: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()

        setAdapterViewPager()

        syncTabWithViewPager()
    }

    private fun syncTabWithViewPager() {
        TabLayoutMediator(tabLayout, viewPagerMain) { tab, position ->
            tab.text = when (position) {
                0 -> "عمومی"
                1 -> "ذخیره شده"
                else -> throw Exception("Invalid Position!")
            }
        }.attach()
    }

    private fun bindView() {
        viewPagerMain = binding.viewPagerMain
        tabLayout = binding.tabLayoutMain
    }

    private fun setAdapterViewPager() {
        val adapter = FragmentMessageStateAdapter(this, COUNT_VIEW_PAGER_SIZE)
        viewPagerMain.adapter = adapter
        viewPagerMain.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}