package com.application.ebcom.feature.message.presetation.screen.saved_message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.ebcom.R
import com.application.ebcom.databinding.FragmentSavedBinding
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.model.SelectItemState
import com.application.ebcom.feature.message.presetation.MessageViewModel
import com.application.ebcom.feature.message.presetation.adapter.MessageAdapter
import com.application.ebcom.feature.message.presetation.adapter.callback.ItemMessageCallback
import com.application.ebcom.util.shareMessage
import com.application.ebcom.util.showEmptyState
import com.application.ebcom.util.showLoading

class SavedMessageFragment : Fragment(), ItemMessageCallback {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageRv: RecyclerView
    private val messageViewModel: MessageViewModel by activityViewModels()
    private lateinit var adapter: MessageAdapter


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

        messageViewModel.messageDataLocal.observe(viewLifecycleOwner) {
            if (it.none { data -> data.saved }) emptyState()
            else successState(it.filter { data -> data.saved })
        }

        binding.cancelBtn.setOnClickListener {
           cancelDelete()
        }

        binding.deleteBtn.setOnClickListener {
            deleteItems()
        }
    }

    private fun cancelDelete() {
        adapter.unSelectItemPosition()
        binding.buttonsLayout.visibility=View.GONE
    }

    private fun deleteItems() {
        messageViewModel.deleteMessages(adapter.messageList
            .filter { it.selected == SelectItemState.SELECTED }
            .map { it.id!! })
    }


    private fun onBind() {
        messageRv = binding.messageSavedRv
    }

    private fun setAdapterRv() {
        adapter = MessageAdapter(callback = this)
        messageRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        messageRv.adapter = adapter
    }

    private fun successState(data: List<Message>) {
        showLoading(mustShow = false)
        adapter.messageList = data
        adapter.notifyDataSetChanged()

        //diffUtil
//        adapter.setNewData(data)
    }

    private fun emptyState() {
        showEmptyState(R.layout.view_empty_state)
    }


    companion object {
        fun newInstance(): SavedMessageFragment {
            val args = Bundle()
            val fragment = SavedMessageFragment()
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

    override fun onLongClicked(isSelected: Boolean) {
        if (!isSelected) {
            binding.buttonsLayout.visibility = View.GONE
        } else {
            binding.buttonsLayout.visibility = View.VISIBLE
        }
    }
}