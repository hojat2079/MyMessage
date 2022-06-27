package com.application.ebcom.feature.message.presetation.screen.public_message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.ebcom.databinding.FragmentPublicBinding
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.presetation.MessageViewModel
import com.application.ebcom.feature.message.presetation.adapter.MessageAdapter
import com.application.ebcom.util.Resource
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class PublicMessageFragment : Fragment() {
    private var _binding: FragmentPublicBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageRv: RecyclerView
    private val messageViewModel: MessageViewModel by activityViewModels()
    private lateinit var adapter: MessageAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicBinding.inflate(inflater, container, false)
        onBind()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapterRv()

        messageViewModel.messageDataRemote.observe(viewLifecycleOwner) {
            Timber.e("data : ${it.data} , error : ${it.message} , type : ${it is Resource.Loading}")
            if (it is Resource.Success){
                adapter.messageList=it.data!!
                Timber.e("data : ${it.data} , error : ${it.message} , type : ${it is Resource.Loading}")
            }

        }
    }

    private fun setAdapterRv() {
        adapter = MessageAdapter()
        messageRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        messageRv.adapter = adapter
    }

    private fun onBind() {
        messageRv = binding.messagePublicRv
    }

    companion object {
        fun newInstance(): PublicMessageFragment {
            val args = Bundle()
            val fragment = PublicMessageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}