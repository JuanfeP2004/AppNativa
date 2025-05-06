package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.xacarana.milistademercado.ui.theme.ScreenPadding // <-- IMPORTANTE

@Composable
fun Register(navController: NavController, authenticator: Auth) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var messageError by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEFCF5))
    ) {
        Column(
            modifier = ScreenPadding.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "Imagen del Registro",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Text(
                text = "MI LISTA DE MERCADO",
                color = Color(0xFF2ECC71),
                fontSize = 24.sp
            )

            Text(text = "¡Bienvenido!", color = Color(0xFF262626), fontSize = 18.sp)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            if (messageError.isNotEmpty()) {
                Text(text = messageError, color = Color.Red)
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71))
            ) {
                Text("Registrarse", color = Color.White)
            }

            TextButton(onClick = { navController.navigate("login") }) {
                Text("Ya tengo una cuenta", color = Color(0xFF262626))
            }
        }
    }
}

