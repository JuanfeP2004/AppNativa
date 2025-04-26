package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
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
import com.xacarana.milistademercado.models.User

@Composable
fun ViewList(navController: NavController, user: User){
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

            TextField(value = "Nombre de la lista", onValueChange = {})

            Text("Descripción:")

            TextField(value = "", onValueChange = {})

            Row {
                Text("Fecha:")
                Text("")
            }

            Box() {
                Column {

                    Text("Objetos")
                    LazyColumn {
                        item {
                            ProductListWidget()
                        }
                        item {
                            ProductListWidget()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductListWidget() {
    Box(){
        Row {
            Image(painter = painterResource(id = R.drawable.tomate), contentDescription = "tomate")
            Column {
                Text("Tomates")
                Text("Unidades: 20kg")
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