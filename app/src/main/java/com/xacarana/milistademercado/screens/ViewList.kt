package com.xacarana.milistademercado.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ViewList(navController: NavController){
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
                    }
                }
            }
        }
    }
}