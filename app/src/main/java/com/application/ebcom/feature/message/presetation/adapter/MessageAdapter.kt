package com.application.ebcom.feature.message.presetation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.application.ebcom.R
import com.application.ebcom.databinding.ItemMessageBinding
import com.application.ebcom.feature.message.domain.model.ExpandItemState
import com.application.ebcom.util.CONST_DATE
import com.application.ebcom.util.toFaNumber
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.model.SelectItemState
import com.application.ebcom.feature.message.presetation.adapter.callback.ItemMessageCallback
import com.application.ebcom.feature.message.presetation.adapter.diffutil.MessageAdapterDiffUtil
import com.application.ebcom.util.MIN_LENGTH_TEXT


class MessageAdapter(
    var messageList: List<Message> = emptyList(),
    val callback: ItemMessageCallback
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(private val bind: ItemMessageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun onBind(messageItem: Message) {
            bind.titleTv.text = messageItem.title
            bind.descTv.text = messageItem.description
            bind.dateTv.text = CONST_DATE.toFaNumber()
            bind.expireDateTv.text = CONST_DATE.toFaNumber()
            if (messageItem.image?.isNotEmpty() == true) loadImage(messageItem.image)
            if (messageItem.unread) unreadSetting() else readSetting()
            if (messageItem.saved) coloredSavedIcon() else notColoredSavedIcon()
            if (messageItem.expand == ExpandItemState.EXPANDED) expandItem(messageItem.image?.isNotEmpty() == true)
            else if (messageItem.expand == ExpandItemState.UNEXPANDED) notExpandItem(
                messageItem.image?.isNotEmpty() == true
            )else bind.expandIv.visibility=View.GONE
            //click listener
            bind.expandIv.setOnClickListener { arrowIconClicked(messageItem) }
            bind.saveMessageIcon.setOnClickListener { saveIconClicked(messageItem) }
            bind.shareMessageIcon.setOnClickListener {
                callback.onShareClicked(
                    message = messageItem.description,
                    subject = messageItem.title
                )
            }
            bind.itemLayout.setOnLongClickListener {
                longClick(messageItem)
                true
            }
            bind.itemLayout.setOnClickListener { click(messageItem) }
            if (messageItem.selected == SelectItemState.SELECTED) {
                bind.doneCheckBox.visibility = View.VISIBLE
                bind.doneCheckBox.setImageResource(R.drawable.ic_check)
                bind.doneCheckBox.setBackgroundResource(R.drawable.bg_check)
            } else if (messageItem.selected == SelectItemState.UNSELECTED) {
                bind.doneCheckBox.setBackgroundResource(R.drawable.bg_check_dis)
                bind.doneCheckBox.visibility = View.VISIBLE
                bind.doneCheckBox.setImageDrawable(null)
            } else{
                bind.doneCheckBox.visibility = View.GONE
            }
        }

        private fun click(messageItem: Message) {
            if (getSelectedNote().isEmpty()) {
                callback.onClicked()

            } else {
                if (messageItem.selected == SelectItemState.SELECTED) {
                    messageItem.selected = SelectItemState.UNSELECTED
                    bind.doneCheckBox.setImageDrawable(null)
                    bind.doneCheckBox.setBackgroundResource(R.drawable.bg_check_dis)
                    if (getSelectedNote().isEmpty()) {
                        callback.onLongClicked(false)
                        unSelectItemPosition()
                    }
                } else {
                    selectItemPosition()
                    messageItem.selected = SelectItemState.SELECTED
                    bind.doneCheckBox.setImageResource(R.drawable.ic_check)
                    bind.doneCheckBox.setBackgroundResource(R.drawable.bg_check)
                    callback.onLongClicked(true)
                }
            }
        }

        private fun longClick(messageItem: Message) {
            if (messageItem.selected == SelectItemState.SELECTED) {
                messageItem.selected = SelectItemState.UNSELECTED
                bind.doneCheckBox.visibility = View.VISIBLE
                bind.doneCheckBox.setImageDrawable(null)
                bind.doneCheckBox.setBackgroundResource(R.drawable.bg_check_dis)
                if (getSelectedNote().isEmpty()) {
                    callback.onLongClicked(false)
                    unSelectItemPosition()
                }
            } else {
                if (getSelectedNote().isEmpty())
                    selectItemPosition()
                messageItem.selected = SelectItemState.SELECTED
                bind.doneCheckBox.setImageResource(R.drawable.ic_check)
                bind.doneCheckBox.setBackgroundResource(R.drawable.bg_check)
                bind.doneCheckBox.visibility = View.VISIBLE
                callback.onLongClicked(true)
            }
        }

        private fun getSelectedNote(): List<Message> {
            val selectedMessage = ArrayList<Message>()
            messageList.forEach { message ->
                if (message.selected == SelectItemState.SELECTED)
                    selectedMessage.add(message)
            }
            return selectedMessage
        }

        private fun saveIconClicked(messageItem: Message) {
            if (!messageItem.saved) {
                callback.onSaveClicked(messageList.indexOf(messageItem), messageItem.id!!)
            } else {
                callback.onUnSaveClicked(messageList.indexOf(messageItem), messageItem.id!!)
            }
        }




        private fun notExpandItem(showImage: Boolean) {
            bind.expandIv.visibility=View.VISIBLE
            bind.descTv.maxLines = MIN_LENGTH_TEXT
            if (showImage) {
                bind.LargeBannerItemIv.visibility = View.GONE
                bind.smallBannerItemIv.visibility = View.VISIBLE
            }
            bind.expandIv.setImageResource(R.drawable.ic_arrow_down)
        }

        private fun expandItem(showImage: Boolean) {
            bind.expandIv.visibility=View.VISIBLE
            bind.descTv.maxLines = Int.MAX_VALUE
            if (showImage) {
                bind.LargeBannerItemIv.visibility = View.VISIBLE
                bind.smallBannerItemIv.visibility = View.GONE
            }
            bind.expandIv.setImageResource(R.drawable.ic_arrow_up)
        }

        private fun arrowIconClicked(messageItem: Message) {
            if (messageItem.expand == ExpandItemState.UNEXPANDED) {
                messageItem.expand = ExpandItemState.EXPANDED
                callback.onReadClicked(messageList.indexOf(messageItem), messageItem.id!!)
            } else messageItem.expand = ExpandItemState.EXPANDED
            notifyItemChanged(messageList.indexOf(messageItem))
        }

        private fun notColoredSavedIcon() {
            bind.saveMessageIcon.setImageResource(R.drawable.ic_save)
        }

        private fun coloredSavedIcon() {
            bind.saveMessageIcon.setImageResource(R.drawable.ic_save_colored)
        }

        private fun loadImage(url: String) {
            bind.smallBannerItemIv.visibility = View.VISIBLE
            bind.smallBannerItemIv.load(url)
            bind.LargeBannerItemIv.load(url)
        }

        private fun unreadSetting() {
            bind.cardViewLayout.setBackgroundResource(R.drawable.bg_item)
            setBoldFontStyle(bind.titleTv)
            setMediumFontStyle(bind.descTv)
        }

        private fun readSetting() {
            bind.cardViewLayout.setBackgroundResource(R.drawable.bg_item_dis)
            setRegularFontStyle(bind.titleTv)
            setRegularFontStyle(bind.descTv)
        }

        private fun setRegularFontStyle(textView: TextView) {
            val typeface = ResourcesCompat.getFont(
                textView.context, R.font.iran_yekan_regular
            )
            textView.typeface = typeface
        }

        private fun setBoldFontStyle(textView: TextView) {
            val typeface = ResourcesCompat.getFont(
                textView.context, R.font.iran_yekan_bold
            )
            textView.typeface = typeface
        }

        private fun setMediumFontStyle(textView: TextView) {
            val typeface = ResourcesCompat.getFont(
                textView.context, R.font.iran_yekan_medium
            )
            textView.typeface = typeface
        }
    }
    private fun selectItemPosition() {
        messageList.forEach {
            it.expand = ExpandItemState.GONE
            if (it.selected == SelectItemState.GONE)
                it.selected = SelectItemState.UNSELECTED
        }
        notifyDataSetChanged()
        //diffUtil
        setNewData(messageList)
    }

     fun unSelectItemPosition() {
        messageList.forEach {
            it.expand = ExpandItemState.UNEXPANDED
            it.selected = SelectItemState.GONE
        }
         notifyDataSetChanged()
         //diffUtil
//         setNewData(messageList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(messageList[position])
    }

    override fun getItemCount(): Int = messageList.size

    fun setNewData(newNotes: List<Message>) {
        val notesMainDiffUtil = MessageAdapterDiffUtil(this.messageList, newNotes)
        val diffUtilResult = DiffUtil.calculateDiff(notesMainDiffUtil)
        this.messageList = newNotes
        diffUtilResult.dispatchUpdatesTo(this)

    }
}