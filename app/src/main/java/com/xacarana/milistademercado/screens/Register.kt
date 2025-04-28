package com.xacarana.milistademercado.screens

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.models.User

@Composable
fun Register(navController: NavController, authenticator: Auth){

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var messageError by remember { mutableStateOf("") }

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
            TextField(value = name, onValueChange = { name = it })

            Text("INGRESA TU EMAIL")
            TextField(value = email, onValueChange = { email = it })

            Text("INGRESA TU CONTRASEÑA")
            TextField(value = password, onValueChange = { password = it })

            Text(messageError)

            Button(onClick = {
                messageError = ""
                val register = authenticator.registrar(
                    name,
                    email,
                    password,
                    { navController.navigate("login") },
                    { messageError = it })
            }) {
                Text("Registrarse")
            }
            Button(onClick = {navController.navigate("login")}) {
                Text("Ya tengo un usuario")
            }
        }
    }
}