package com.xacarana.milistademercado.models
import java.time.LocalDate
import java.util.Date
import kotlin.collections.List

class MarketList(
    name: String,
    description: String,
    date: LocalDate,
    products: List<Product>) {
    var name: String = name
    var description: String = description
    var date: LocalDate = date
    var products: List<Product> = products
}