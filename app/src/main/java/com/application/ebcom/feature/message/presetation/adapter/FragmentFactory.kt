package com.application.ebcom.feature.message.presetation.adapter

import androidx.fragment.app.Fragment
import com.application.ebcom.feature.message.presetation.screen.public_message.PublicMessageFragment
import com.application.ebcom.feature.message.presetation.screen.saved_message.SavedMessageFragment
import com.application.ebcom.util.FRAGMENT_PUBLIC_INDEX
import com.application.ebcom.util.FRAGMENT_SAVED_INDEX

class FragmentFactory {

    fun getFragment(index: Int): Fragment {
        return when (index) {
            FRAGMENT_PUBLIC_INDEX -> PublicMessageFragment.newInstance()
            FRAGMENT_SAVED_INDEX -> SavedMessageFragment.newInstance()
            else -> throw Exception("Invalid index")
        }
    }
}
