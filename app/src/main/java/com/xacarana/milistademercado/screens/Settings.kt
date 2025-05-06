package com.xacarana.milistademercado.screens

import androidx.compose.foundation.layout.*
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
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.ui.theme.ScreenPadding

@Composable
fun SettingsScreen(navController: NavController, user: User, authenticator: Auth) {
    var darkThemeEnabled by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = ScreenPadding.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Encabezado
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                    Text(
                        text = "Ajustes",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Modo oscuro
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Modo oscuro", fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Switch(
                        checked = darkThemeEnabled,
                        onCheckedChange = { darkThemeEnabled = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Cerrar sesión
                Button(
                    onClick = {
                        authenticator.cerrarSesion(user) {
                            navController.navigate("login") {
                                popUpTo("menu") { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6D6D6)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("CERRAR SESIÓN", color = Color.Black)
                }
            }

            // Créditos
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(color = Color.Gray, thickness = 1.dp)
                Text("Créditos", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Juan Felipe Ramírez", fontSize = 14.sp)
                Text("Nicolle Aguirre", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

