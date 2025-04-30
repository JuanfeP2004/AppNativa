package com.xacarana.milistademercado.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class User (
    id: String = "",
    name: String = "",
    listas: MutableList<MarketList> = mutableListOf<MarketList>()
) : ViewModel() {

    private val _id = MutableLiveData<String>().apply { value = id }
    val id: LiveData<String> get() = _id

    private val _name = MutableLiveData<String>().apply { value = name }
    val name: LiveData<String> get() = _name

    private val _listas = MutableLiveData<MutableList<MarketList>>().apply { value = listas }
    val listas: LiveData<MutableList<MarketList>> get() = _listas

    fun ModifyId(id: String){
        _id.value = id
    }

    fun ModifyName(name: String){
        _name.value = name
    }

    fun ModifyList(list: MutableList<MarketList>){
        _listas.value = list
    }

    fun AddList(list: MarketList){
        _listas.value?.add(list)
    }
}