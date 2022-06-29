package com.application.ebcom.feature.message.presetation.screen.public_message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.ebcom.R
import com.application.ebcom.databinding.FragmentPublicBinding
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.model.SelectItemState
import com.application.ebcom.feature.message.presetation.MessageViewModel
import com.application.ebcom.feature.message.presetation.adapter.MessageAdapter
import com.application.ebcom.feature.message.presetation.adapter.callback.ItemMessageCallback
import com.application.ebcom.util.*
import timber.log.Timber

class PublicMessageFragment : Fragment(), ItemMessageCallback {
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

        messageViewModel.loadingObs.observe(viewLifecycleOwner) {
            loadingState()
        }

        messageViewModel.errorObs.observe(viewLifecycleOwner) {
            errorState()
        }
        messageViewModel.messageDataLocal.observe(viewLifecycleOwner) {
            if (it.isEmpty()) emptyState()
            else successState(it)
        }

        binding.cancelBtn.setOnClickListener {
            cancelDelete()
        }

        binding.deleteBtn.setOnClickListener {
            deleteItems()
        }
    }

    private fun deleteItems() {
        messageViewModel.deleteMessages(adapter.messageList
            .filter { it.selected == SelectItemState.SELECTED }
            .map { it.id!! })
    }

    private fun emptyState() {
        showEmptyState(R.layout.view_empty_state)
    }

    private fun setAdapterRv() {
        adapter = MessageAdapter(callback = this)
        messageRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        messageRv.adapter = adapter
    }

    private fun onBind() {
        messageRv = binding.messagePublicRv
    }

    private fun errorState() {
        showLoading(false)
        showError()
    }

    private fun loadingState() {
        showLoading(mustShow = true)
    }

    private fun successState(data: List<Message>) {
        showLoading(mustShow = false)
        adapter.messageList = data
        adapter.notifyDataSetChanged()

        //diffUtil
        adapter.setNewData(data)
    }


    companion object {
        fun newInstance(): PublicMessageFragment {
            val args = Bundle()
            val fragment = PublicMessageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onShareClicked(message: String, subject: String) {
        shareMessage(message, subject)
    }

    override fun onSaveClicked(index: Int, id: Int) {
        messageViewModel.saveMessage(index, id)
    }

    override fun onReadClicked(index: Int, id: Int) {
        messageViewModel.readMessage(index, id)
    }

    override fun onUnSaveClicked(index: Int, id: Int) {
        messageViewModel.unSaveMessage(index, id)
    }

    private fun cancelDelete() {
        adapter.unSelectItemPosition()
        binding.buttonsLayout.visibility = View.GONE
    }


    override fun onClicked() {
        //todo
    }

    override fun onLongClicked(isSelected: Boolean) {
        if (!isSelected) {
            binding.buttonsLayout.visibility = View.GONE
        } else {
            binding.buttonsLayout.visibility = View.VISIBLE
        }
    }
}