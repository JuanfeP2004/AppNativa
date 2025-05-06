package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
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

@Composable
fun ViewList(navController: NavController, list: ViewListModel, db: Database) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { navController.navigate("menu") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7EECA5))
                ) {
                    Text("Regresar", color = Color.White)
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar lista", color = Color.White)
                }
            }

            OutlinedTextField(
                value = list.list.value!!.name,
                onValueChange = { _ -> },
                readOnly = true,
                label = { Text("Nombre de la lista") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Descripción:", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = list.list.value!!.description,
                onValueChange = { _ -> },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row {
                Text("Fecha: ", fontWeight = FontWeight.Bold)
                Text(list.list.value!!.date.toString())
            }

            Text(
                "Productos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2ECC71)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(list.list.value!!.products) { product ->
                    ProductListWidget(product, list.list.value!!)
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
                "checked" -> Color(0xFFDEE9D8)
                "deleted" -> Color(0xFFFFD6D6)
                else -> Color.White
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
                    fontSize = 16.sp
                )
                Text(
                    text = "Unidades: ${product.amount}${product.und}",
                    textDecoration = if (check != "none") TextDecoration.LineThrough else null
                )
                if (check != "none") {
                    Text(
                        text = if (check == "checked") "COMPRADO" else "ELIMINADO",
                        color = if (check == "checked") Color(0xFF2ECC71) else Color.Red,
                        fontWeight = FontWeight.Bold
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
                                usuario.listas.value?.find { it.id == list.id }
                                    ?.products?.find { it.id == product.id }?.check = check
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
                                usuario.listas.value?.find { it.id == list.id }
                                    ?.products?.find { it.id == product.id }?.check = check
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
