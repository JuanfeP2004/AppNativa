package com.xacarana.milistademercado.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Register(navController: NavController){
    Surface() {
        Text("Register")
        Button(onClick = {navController.navigate("login")}) {
            Text("Ir al login")
        }
    }
}