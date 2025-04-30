package com.xacarana.milistademercado.models
import java.time.LocalDate
import java.util.Date
import kotlin.collections.List

class MarketList(
    id: String,
    name: String,
    description: String,
    date: LocalDate,
    completion: Float,
    products: List<Product>) {
    var id: String = id
    var name: String = name
    var description: String = description
    var date: LocalDate = date
    var completion = completion
    var products: List<Product> = products
}