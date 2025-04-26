package com.xacarana.milistademercado.models
import java.util.Date
import kotlin.collections.List

class MarketList(
    name: String,
    description: String,
    date: Date,
    products: List<Product>) {
    val name: String = name
    val description: String = description
    val date: Date = date
    val products: List<Product> = products
}