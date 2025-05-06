package com.xacarana.milistademercado.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.ViewListModel
import com.xacarana.milistademercado.ui.theme.ScreenPadding
import com.xacarana.milistademercado.viewlist
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Menu(navController: NavController, user: User, db: Database, viewmodel: ViewListModel) {
    var messageError by remember { mutableStateOf("") }
    var chargeMessage by remember { mutableStateOf("Cargando listas...") }
    var items by remember { mutableStateOf<List<MarketList>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            items = db.getUserList(user.id.value!!) { messageError = it }
            if (items.isEmpty()) chargeMessage = "No tienes ninguna lista"
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .then(ScreenPadding)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            // Encabezado y botón de configuración
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Bienvenido, ${user.name.value}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2ECC71)
                    )
                    Text(
                        text = "¿Qué quieres comprar hoy?",
                        fontSize = 16.sp,
                        color = Color(0xFF262626)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.navigate("settings")
                        }
                )
            }

            // Botón de nueva lista
            Button(
                onClick = { navController.navigate("create-list") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7EECA5)),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Crear nueva lista", color = Color.White)
            }

            // Título de listas
            Text(
                text = "Mis listas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2ECC71)
            )

            // Mostrar errores o estado
            if (messageError.isNotEmpty()) {
                Text(text = messageError, color = Color.Red)
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (items.isEmpty()) {
                    item {
                        Text(chargeMessage)
                    }
                } else {
                    items(items) { elemento ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewlist.ModifyList(elemento)
                                    navController.navigate("view-list")
                                },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFEEFCF5))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = elemento.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color(0xFF2ECC71)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("Para: ${elemento.date}")
                                        Text("Objetos: ${elemento.products.size}")
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Completado:")
                                        Text(
                                            "${elemento.completion}%",
                                            color = Color(0xFF2ECC71),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




