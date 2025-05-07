package com.xacarana.milistademercado.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.ThemeViewModel
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.ViewListModel
import com.xacarana.milistademercado.ui.theme.*
import com.xacarana.milistademercado.viewlist
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Menu(navController: NavController, user: User, db: Database, viewmodel: ViewListModel, Theme: ThemeViewModel) {
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Icono ajustes
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                IconButton(
                    onClick = { navController.navigate("settings") },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                        .border(1.dp, Color.LightGray, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Ajustes",
                        tint = Color.Black
                    )
                }
            }

            // Bienvenida
            Text(
                text = "Bienvenido\n(${user.name.value})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.SansSerif
            )

            Text(
                text = "¿Qué quieres comprar hoy?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.SansSerif
            )

            // Botón crear lista
            Button(
                onClick = { navController.navigate("create-list") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("CREAR LISTA", color = MaterialTheme.colorScheme.onSurface, fontFamily = FontFamily.SansSerif)
            }

            // Título listas con línea al lado
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mis Listas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.weight(1f)
                )
            }

            if (messageError.isNotEmpty()) {
                Text(text = messageError, color = Color.Red)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (items.isEmpty()) {
                    item {
                        Text(chargeMessage, color = MaterialTheme.colorScheme.onSurface)
                    }
                } else {
                    items(items) { elemento ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewlist.ModifyList(elemento)
                                    navController.navigate("view-list")
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = elemento.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "Completado:",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "${elemento.completion}%",
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 14.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Para: ${elemento.date}",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Objetos: ${elemento.products.size}",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}





