package com.application.ebcom.feature.message.presetation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.application.ebcom.R
import com.application.ebcom.util.CONST_DATE
import com.application.ebcom.util.toFaNumber
import com.application.ebcom.databinding.ItemMessageBinding
import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import com.application.ebcom.feature.message.domain.model.Message


class MessageAdapter(
    var messageList: List<Message> = emptyList()
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(private val bind: ItemMessageBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun onBind(messageItem:Message) {
            bind.titleTv.text = messageItem.title
            bind.descTv.text = messageItem.description
            bind.dateTv.text = CONST_DATE.toFaNumber()
            bind.expireDateTv.text = CONST_DATE.toFaNumber()
                if (messageItem.image?.isNotEmpty() == true) loadImage(messageItem.image)
            if (messageItem.unread) unreadSetting()
            else readSetting()
        }

        private fun loadImage(url: String) {
            bind.smallBannerItemIv.load(url)
            bind.LargeBannerItemIv.load(url)
        }

        private fun unreadSetting() {
            bind.cardViewLayout.setBackgroundResource(R.color.backgroundDisable)
            setRegularFontStyle(bind.titleTv)
            setRegularFontStyle(bind.descTv)
        }

        private fun readSetting() {
            bind.cardViewLayout.setBackgroundResource(R.color.backgroundWhite)
            setBoldFontStyle(bind.titleTv)
            setMediumFontStyle(bind.descTv)
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

}