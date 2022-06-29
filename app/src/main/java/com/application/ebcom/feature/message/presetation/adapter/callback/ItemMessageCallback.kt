package com.application.ebcom.feature.message.presetation.adapter.callback

interface ItemMessageCallback {
    fun onShareClicked(message:String,subject:String)
    fun onSaveClicked(index:Int,id:Int)
    fun onReadClicked(index:Int,id:Int)
    fun onUnSaveClicked(index:Int,id:Int)
    fun onClicked()
    fun onLongClicked(isSelected:Boolean)
}