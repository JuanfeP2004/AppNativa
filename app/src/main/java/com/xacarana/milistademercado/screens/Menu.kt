package com.xacarana.milistademercado.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun Menu(navController: NavController){
    Surface {
        Column {
            Text("BIENVENIDO USER")
            Text("Que quieres comprar hoy?")

            Button(onClick = { navController.navigate("create-list") }) {
                Text("CREAR LISTA")
            }

            Text("MIS LISTAS")

            LazyColumn {
                item {
                    ListWidget(navController)
                }
                item {
                    ListWidget(navController)
                }
            }
        }
    }
}

@Composable
fun ListWidget(navController: NavController){
    Box(
        modifier = Modifier.clickable {
            navController.navigate("view-list")
        }
    ){
        Box(){}
        Text("title")
        Row {
            Column {
                Text("Para: ")
                Text("Objetos: ")
            }
            Column {
                Text("Completado:")
                Text("X%")
            }
        }
    }
}