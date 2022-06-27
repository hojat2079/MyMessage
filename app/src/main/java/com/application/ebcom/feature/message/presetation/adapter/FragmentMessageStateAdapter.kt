package com.application.ebcom.feature.message.presetation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.ebcom.feature.message.presetation.screen.public_message.PublicMessageFragment
import com.application.ebcom.feature.message.presetation.screen.saved_message.SavedMessageFragment

class FragmentMessageStateAdapter(activity: AppCompatActivity, val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> PublicMessageFragment.newInstance()
            1-> SavedMessageFragment.newInstance()
            else -> throw Exception("Invalid position!")
        }
    }
}