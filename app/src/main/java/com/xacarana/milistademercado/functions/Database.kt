package com.xacarana.milistademercado.functions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.Product
import com.xacarana.milistademercado.models.User
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Database : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    /*suspend fun getUserList(user: User): List<String> {
        val snapshot = db.collection("Usuario").get().await()
        return snapshot.documents.mapNotNull { it.getString("campo") }
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserList(
        id: String,
        //onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) : MutableList<MarketList> {

        try {

            val listCollection = db.collection("Usuarios").document(id)
                .collection("Listas")
            val snapshot = listCollection.get().await()

            val list: MutableList<MarketList> = mutableListOf()
            //val productList: MutableList<Product> = mutableListOf()

            for (document in snapshot.documents){

                val productList: MutableList<Product> = mutableListOf()
                val docProductos = listCollection.document(document.id).collection("Productos").get().await()
                productList.clear()

                for (producto in docProductos.documents){
                    productList.add(Product(
                        name = producto.get("name").toString(),
                        amount = producto.get("amount").toString().toFloat(),
                        idPhoto = producto.get("idImage").toString().toInt(),
                        und = producto.get("units").toString(),
                        check = producto.get("check").toString()
                    ))
                }

                list.add(MarketList(
                    name = document.get("name").toString(),
                    description = document.get("description").toString(),
                    date = LocalDate.parse(document.get("date") as CharSequence?, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    completion = document.get("completion").toString().toFloat(),
                    products = productList
                ))
            }

            return list

        } catch (e: Exception){
            val mensaje = e?.message ?: "Error desconocido"
            onFailure(mensaje)
            return mutableListOf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createList(
        list: MarketList,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit) {

        val userdoc = db.collection("Usuarios").document(user.id.value!!)
        val newList = userdoc.collection("Listas").document()

        newList.set(
            mapOf(
                "name" to list.name,
                "description" to list.description,
                "date" to list.date.toString(),
                "completion" to list.completion
            )
        )

        list.products.forEach { product ->
            newList.collection("Productos").document().set(
                mapOf(
                    "name" to product.name,
                    "idImage" to product.idProduct,
                    "amount" to product.amount,
                    "units" to product.und,
                    "check" to product.check
                )
            )
        }

        user.AddList(list)
        onSuccess()
    }
}