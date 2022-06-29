package com.application.ebcom.feature.message.presetation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.ebcom.feature.message.presetation.screen.public_message.PublicMessageFragment
import com.application.ebcom.feature.message.presetation.screen.saved_message.SavedMessageFragment
import com.application.ebcom.util.FRAGMENT_PUBLIC_INDEX
import com.application.ebcom.util.FRAGMENT_SAVED_INDEX

class FragmentMessageStateAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment {
        val factory = FragmentFactory()
        return when (position) {
            FRAGMENT_PUBLIC_INDEX -> factory.getFragment(FRAGMENT_PUBLIC_INDEX)
            FRAGMENT_SAVED_INDEX -> factory.getFragment(FRAGMENT_SAVED_INDEX)
            else -> throw Exception("Invalid position!")
        }
    }

}