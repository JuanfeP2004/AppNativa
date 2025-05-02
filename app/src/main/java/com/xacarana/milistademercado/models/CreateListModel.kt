package com.xacarana.milistademercado.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateListModel(list: MarketList) : ViewModel() {

    private val _list = MutableLiveData<MarketList>().apply { value = list }
    val list: LiveData<MarketList> get() = _list

    fun ModifyList(list: MarketList){
        _list.value = list
    }
}
