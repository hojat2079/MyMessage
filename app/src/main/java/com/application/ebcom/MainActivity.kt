package com.application.ebcom

import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.application.ebcom.databinding.ActivityMainBinding
import com.application.ebcom.feature.message.domain.model.CountMessage
import com.application.ebcom.feature.message.presetation.adapter.FragmentMessageStateAdapter
import com.application.ebcom.util.COUNT_VIEW_PAGER_SIZE
import com.application.ebcom.util.FRAGMENT_PUBLIC_INDEX
import com.application.ebcom.util.convertDpToPixel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber


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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun createBadgeItemCount(countMessage: CountMessage) {
        Timber.e("show badge!!")
        val badge = tabLayout.getTabAt(FRAGMENT_PUBLIC_INDEX)?.orCreateBadge
        badge?.apply {
            badgeGravity = BadgeDrawable.TOP_START
            isVisible = countMessage.count>0
            number = countMessage.count
            badgeGravity = BadgeDrawable.BOTTOM_END
            verticalOffset = convertDpToPixel(10f, this@MainActivity).toInt()
            horizontalOffset =convertDpToPixel(-10f, this@MainActivity).toInt()
            backgroundColor = resources.getColor(R.color.red)
        }
    }
    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}