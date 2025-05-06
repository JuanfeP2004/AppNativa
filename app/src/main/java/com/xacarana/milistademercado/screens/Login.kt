package com.xacarana.milistademercado.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.ui.theme.BackgroundLight
import com.xacarana.milistademercado.ui.theme.GreenPrimary
import com.xacarana.milistademercado.ui.theme.TextColor
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
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "Imagen de inicio",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "LISTA DIGITAL",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Inicio de sesión",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextColor,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Ingresa tu correo:",
                fontSize = 20.sp,
                color = TextColor,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                textStyle = TextStyle(fontFamily = FontFamily.SansSerif),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = GreenPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ingresa tu contraseña:",
                fontSize = 18.sp,
                color = TextColor,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(fontFamily = FontFamily.SansSerif),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = GreenPrimary
                )
            )

            if (messageError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = messageError,
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("INGRESAR", fontFamily = FontFamily.SansSerif, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("REGISTRARSE", fontFamily = FontFamily.SansSerif, color = Color.White)
            }
        }
    }
}


