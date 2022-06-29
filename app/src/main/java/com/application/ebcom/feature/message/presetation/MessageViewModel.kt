package com.application.ebcom.feature.message.presetation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.ebcom.feature.message.domain.model.CountMessage
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.use_case.MessageUseCases
import com.application.ebcom.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val useCases: MessageUseCases
) : ViewModel() {
    private val _messageDataLocal = MutableLiveData<List<Message>>()
    val messageDataLocal: LiveData<List<Message>> get() = _messageDataLocal

    private val _loadingObs = MutableLiveData<Boolean>()
    val loadingObs: LiveData<Boolean> get() = _loadingObs

    private val _errorObs = MutableLiveData<Boolean>()
    val errorObs: LiveData<Boolean> get() = _errorObs

    init {
        getMessageInApi()
    }

    private fun getMessageInApi() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getAllMessageRemote().collect {
                Timber.e("data : ${it.data} , error : ${it.message} , type : ${it is Resource.Loading}")
                when (it) {
                    is Resource.Loading -> _loadingObs.postValue(true)
                    is Resource.Error -> _errorObs.postValue(true)
                    is Resource.Success -> insertAllItemInLocal(it.data!!)
                }
            }
        }
    }

    private fun insertAllItemInLocal(data: List<Message>) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.addAllMessage(data)
            getMessageInLocal()
        }
    }

    private fun getMessageInLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getAllMessageLocal().collect {
                Timber.e("$it")
                _messageDataLocal.postValue(it.sortedBy { data -> data.id }
                    .sortedBy { data -> !data.unread })

                //show Badge
                EventBus.getDefault().postSticky(CountMessage(it.size))
            }

        }
    }

    fun saveMessage(index: Int, id: Int) {
        messageDataLocal.value!![index].saved = true
        viewModelScope.launch {
            useCases.saveMessage(id)
        }
    }

    fun unSaveMessage(index: Int, id: Int) {
        messageDataLocal.value!![index].saved = false
        viewModelScope.launch {
            useCases.unSaveMessage(id)
        }
    }

    fun readMessage(index: Int, id: Int) {
        messageDataLocal.value!![index].unread = false
        viewModelScope.launch {
            useCases.readMessage(id)
        }
    }

    fun deleteMessages(ids: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.deleteMessages(ids)
        }
    }

}