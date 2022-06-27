package com.application.ebcom.feature.message.presetation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.use_case.MessageUseCases
import com.application.ebcom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val useCases: MessageUseCases
) : ViewModel() {
    private val _messagesDataRemote = MutableLiveData<Resource<List<Message>>>(Resource.Loading())
    val messageDataRemote: LiveData<Resource<List<Message>>> get() = _messagesDataRemote

    init {
        getMessageInApi()
    }

    private fun getMessageInApi() {
        Timber.e("ViewModel call!!")

        viewModelScope.launch(Dispatchers.IO) {
            useCases.getAllMessageRemote().collectLatest {
                Timber.e("data : ${it.data} , error : ${it.message} , type : ${it is Resource.Loading}")
                _messagesDataRemote.postValue(it)
            }
        }
    }
}