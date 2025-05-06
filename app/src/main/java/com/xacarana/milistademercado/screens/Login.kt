package com.xacarana.milistademercado.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.ui.theme.ScreenPadding
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(navController: NavController, user: User, authenticator: Auth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var messageError by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = ScreenPadding.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "Imagen de inicio",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Mi Lista de Mercado",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2ECC71)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Iniciar sesión",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (messageError.isNotEmpty()) {
                Text(
                    text = messageError,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
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
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text("Crear cuenta", color = Color(0xFF2ECC71))
            }
        }
    }
}

