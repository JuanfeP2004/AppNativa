package com.xacarana.milistademercado.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class User (
    id: String = "",
    name: String = "",
    listas: List<MarketList> = emptyList<MarketList>()
) : ViewModel() {

    private val _id = MutableLiveData<String>().apply { value = id }
    val id: LiveData<String> get() = _id

    private val _name = MutableLiveData<String>().apply { value = name }
    val name: LiveData<String> get() = _name

    private val _listas = MutableLiveData<List<MarketList>>().apply { value = listas }
    val listas: LiveData<List<MarketList>> get() = _listas

    fun ModifyId(id: String){
        _id.value = id
    }

    fun ModifyName(name: String){
        _name.value = name
    }

    fun ModifyList(list: List<MarketList>){
        _listas.value = list
    }
}