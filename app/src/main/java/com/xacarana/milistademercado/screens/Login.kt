package com.xacarana.milistademercado.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Login(navController: NavController){
    Surface() {
        Text("Login")
        Button(onClick = {navController.navigate("register")}) {
            Text("Ir al registro")
        }
    }
}