package com.xacarana.milistademercado.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

            }
        }
    }
}