package com.xacarana.milistademercado.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.models.ThemeViewModel
import com.xacarana.milistademercado.models.User

@Composable
fun SettingsScreen(navController: NavController, user: User, authenticator: Auth, Theme: ThemeViewModel) {
    var darkThemeEnabled by remember { mutableStateOf(false) }

    val backgroundColor = if (darkThemeEnabled) Color(0xFF121212) else Color(0xFFF0FFF7)
    val textColor = if (darkThemeEnabled) Color.White else Color.Black
    val buttonColor = Color(0xFF00C853)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            // Botón de regreso y título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = textColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ajustes",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }

            // Botón modo oscuro
            Button(
                onClick = { Theme.toggleTheme() },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
            ) {
                Text(
                    text = "MODO OSCURO",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón cerrar sesión
            Button(
                onClick = {
                    authenticator.cerrarSesion(user) {
                        navController.navigate("login") {
                            popUpTo("menu") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(50.dp)
            ) {
                Text(
                    text = "CERRAR SESIÓN",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            // Créditos
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Divider(color = textColor.copy(alpha = 0.5f), thickness = 1.dp)
                Text(
                    text = "Créditos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Divider(color = textColor.copy(alpha = 0.5f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Juan Felipe Ramírez",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Text(
                    text = "Nicolle Aguirre",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
        }
    }
}



