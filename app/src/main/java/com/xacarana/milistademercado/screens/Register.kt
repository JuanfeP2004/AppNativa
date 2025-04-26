package com.xacarana.milistademercado.screens

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.models.User

@Composable
fun Register(navController: NavController){
    Surface() {
        Column {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "Imagen del Registro",
                contentScale = ContentScale.Fit
            )
            Text("MI LISTA DE MERCADO")

            Text("BIENVENIDO")

            Text("INGRESA TU NOMBRE")
            TextField(value = "Usuario", onValueChange = {})

            Text("INGRESA TU CONTRASEÑA")
            TextField(value = "Contraseña", onValueChange = {})

            Button(onClick = {navController.navigate("login")}) {
                Text("Registrarse")
            }
            Button(onClick = {navController.navigate("login")}) {
                Text("Ya tengo un usuario")
            }
        }
    }
}