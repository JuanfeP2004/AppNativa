package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.Product
import com.xacarana.milistademercado.models.ViewListModel
import com.xacarana.milistademercado.usuario
import com.xacarana.milistademercado.firebase
import com.xacarana.milistademercado.models.ThemeViewModel

@Composable
fun ViewList(navController: NavController, list: ViewListModel, db: Database, Theme: ThemeViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 32.dp, vertical = 24.dp)

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("menu") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("REGRESAR", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
                }
                Button(
                    onClick = {
                        db.deleteList(
                            usuario,
                            list.list.value!!,
                            { navController.navigate("menu") },
                            {}
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ELIMINAR", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
                }
            }

            Text(
                text = list.list.value!!.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Divider(thickness = 1.dp, color = Color.Black.copy(alpha = 0.5f))

            Text(
                text = "Descripcion:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start),
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                value = list.list.value!!.description,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiary),
                maxLines = 3,
                readOnly = true
            )

            Text(
                text = "Para: ${list.list.value!!.date}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start),
                color = MaterialTheme.colorScheme.onSurface
            )

            Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.tertiary)) {
                Text(
                    text = "Objetos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(list.list.value!!.products) { product ->
                        ProductListWidget(product, list.list.value!!)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductListWidget(product: Product, list: MarketList) {
    var check by remember { mutableStateOf(product.check) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (check) {
                "checked" -> Color(0xFFB9EACB)
                "deleted" -> Color(0xFFFFD6D6)
                else -> MaterialTheme.colorScheme.secondary
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.idProduct),
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    textDecoration = if (check != "none") TextDecoration.LineThrough else null,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Unidades: ${product.amount}${product.und}",
                    textDecoration = if (check != "none") TextDecoration.LineThrough else null,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (check != "none") {
                    Text(
                        text = if (check == "checked") "COMPRADO" else "ELIMINADO",
                        color = if (check == "checked") Color(0xFF2ECC71) else Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            if (check == "none") {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                check = "checked"
                                firebase.modifyProduct(check, usuario, list, product)
                                usuario.listas.value?.find { it.id == list.id }?.products?.find { it.id == product.id }?.check = check
                            }
                            .padding(4.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Marcar como comprado",
                        tint = Color(0xFF2ECC71)
                    )
                    Icon(
                        modifier = Modifier
                            .clickable {
                                check = "deleted"
                                firebase.modifyProduct(check, usuario, list, product)
                                usuario.listas.value?.find { it.id == list.id }?.products?.find { it.id == product.id }?.check = check
                            }
                            .padding(4.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Eliminar producto",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}



