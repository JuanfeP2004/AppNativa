package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.Product
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.ViewListModel

@Composable
fun ViewList(navController: NavController, user: User, list: ViewListModel){
    Surface {
        Column {
            Row {
                Button(onClick = { navController.navigate("menu") }) {
                    Text("REGRESAR")
                }
                Button(onClick = { navController.navigate("menu") }) {
                    Text("ELIMINAR")
                }
            }

            TextField(value = list.list.value!!.name, onValueChange = {}, readOnly = true)

            Text("Descripción:")

            TextField(value = list.list.value!!.description, onValueChange = {}, readOnly = true)

            Row {
                Text("Fecha:")
                Text(list.list.value!!.date.toString())
            }

            Box() {
                Column {
                    Text("Objetos")
                    LazyColumn {
                        items(list.list.value!!.products) {
                            element -> ProductListWidget(element)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductListWidget(product: Product) {
    Box(){
        Row {
            Image(painter = painterResource(id = product.idProduct), contentDescription = product.name)
            Column {
                Text(product.name)
                Text("Unidades: ${product.amount}${product.und}")
            }
            Column {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "delete"
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete"
                )
            }
        }
    }
}