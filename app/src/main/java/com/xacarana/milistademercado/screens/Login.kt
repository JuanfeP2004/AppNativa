package com.xacarana.milistademercado.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.authenticator
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.models.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.Authenticator

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(navController: NavController, user: User, authenticator: Auth) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var messageError by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Surface() {
        Column {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "Imagen del Registro",
                contentScale = ContentScale.Fit
            )
            Text("MI LISTA DE MERCADO")

            Text("INICIAR SESION")

            Text("INGRESA TU EMAIL")
            TextField(value = email, onValueChange = { email = it })

            Text("INGRESA TU CONTRASEÑA")
            TextField(value = password, onValueChange = { password = it })

            Text(messageError)

            Button(onClick = {
                messageError = ""
                coroutineScope.launch {
                    authenticator.iniciarSesion(
                        email,
                        password,
                        user,
                        { navController.navigate("menu") },
                        { messageError = it }
                    )
                }
            }) {
                Text("Ingresar")
            }
            Button(onClick = {navController.navigate("register")}) {
                Text("Crear usuario")
            }
        }
    }
}