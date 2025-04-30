package com.xacarana.milistademercado.functions

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.Product
import com.xacarana.milistademercado.models.User
import kotlinx.coroutines.tasks.await

class Database : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    /*suspend fun getUserList(user: User): List<String> {
        val snapshot = db.collection("Usuario").get().await()
        return snapshot.documents.mapNotNull { it.getString("campo") }
    }*/

    fun createList(
        list: MarketList,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit) {

        val user = db.collection("Usuarios").document(user.id.value!!)
        val newList = user.collection("Listas").document()

        newList.set(
            mapOf(
                "name" to list.name,
                "description" to list.description,
                "date" to list.date,
                "completion" to 0.0f
            )
        )

        list.products.forEach { product ->
            newList.collection("Productos").document().set(
                mapOf(
                    "name" to product.name,
                    "idImage" to product.idProduct,
                    "amount" to product.amount,
                    "units" to product.und,
                    "check" to false
                )
            )
        }

        onSuccess()
    }
}