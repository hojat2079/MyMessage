package com.application.ebcom.feature.message.presetation.screen.saved_message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.application.ebcom.databinding.FragmentPublicBinding
import com.application.ebcom.databinding.FragmentSavedBinding
import com.application.ebcom.feature.message.presetation.MessageViewModel
import com.application.ebcom.feature.message.presetation.screen.public_message.PublicMessageFragment

class SavedMessageFragment : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageRv: RecyclerView
    private val messageViewModel: MessageViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        onBind()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterRv()
    }

    private fun setAdapterRv() {
        //todo
    }

    private fun onBind() {
        messageRv = binding.messageSavedRv
    }

    companion object {
        fun newInstance(): SavedMessageFragment {
            val args = Bundle()
            val fragment = SavedMessageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}