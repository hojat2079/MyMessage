package com.application.ebcom.feature.message.presetation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.application.ebcom.feature.message.domain.model.Message

class MessageAdapterDiffUtil(
    private val oldList:List<Message>,
    private val newList:List<Message>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}