package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.models.ThemeViewModel
import com.xacarana.milistademercado.ui.theme.*

@Composable
fun Register(navController: NavController, authenticator: Auth, Theme: ThemeViewModel) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var messageError by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = ScreenPadding
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "Imagen del Registro",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Text(
                text = "LISTA DIGITAL",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Ingresa tu nombre:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Ingresa tu correo:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Ingresa tu contraseña:", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurface) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(12.dp)
            )

            if (messageError.isNotEmpty()) {
                Text(
                    text = messageError,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(
                onClick = {
                    messageError = ""
                    authenticator.registrar(
                        name,
                        email,
                        password,
                        { navController.navigate("login") },
                        { messageError = it }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(50)
            ) {
                Text("REGISTRARSE", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelLarge)
            }

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(50)
            ) {
                Text("YA TENGO UN USUARIO", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}


