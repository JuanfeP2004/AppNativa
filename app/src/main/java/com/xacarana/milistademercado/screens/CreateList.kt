package com.xacarana.milistademercado.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.CreateListModel
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.Product
import com.xacarana.milistademercado.models.User
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateList(
    navController: NavController,
    user: User,
    db: Database,
    list: MutableList<Product>,
    createlist: CreateListModel
) {
    var fecha by remember { mutableStateOf(createlist.list.value?.date!!) }
    var name by remember { mutableStateOf(createlist.list.value?.name!!) }
    var description by remember { mutableStateOf(createlist.list.value?.description!!) }
    var lista = remember { mutableStateListOf(*list.toTypedArray()) }
    var messageError by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val objectlista = MarketList(
        id = "",
        name = name,
        description = description,
        date = fecha,
        completion = 0.0f,
        products = list
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF6FFFA))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    list.clear()
                    createlist.list.value?.apply {
                        name = ""
                        description = ""
                        date = LocalDate.now()
                    }
                    navController.navigate("menu")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB1F4D8))
            ) {
                Text("← Regresar", color = Color.Black)
            }

            Button(
                onClick = {
                    val check = ValidateList(objectlista)
                    if (check.isEmpty()) {
                        messageError = ""
                        db.createList(objectlista, user, {
                            list.clear()
                            createlist.list.value?.apply {
                                name = ""
                                description = ""
                                date = LocalDate.now()
                            }
                            navController.navigate("menu")
                        }, { messageError = it })
                    } else {
                        messageError = check
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7EECA5))
            ) {
                Text("Crear lista", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            label = { Text("Nombre de la lista") },
            value = name,
            onValueChange = {
                name = it
                objectlista.name = it
                createlist.list.value?.name = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            label = { Text("Descripción") },
            value = description,
            onValueChange = {
                description = it
                objectlista.description = it
                createlist.list.value?.description = it
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(12.dp))

        DateTime(
            fechaSeleccionada = fecha,
            onFechaSeleccionada = {
                fecha = it
                objectlista.date = it
                createlist.list.value?.date = it
            }
        )

        if (messageError.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(messageError, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Productos agregados",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2ECC71)
        )

        Button(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            onClick = { navController.navigate("create-product") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF76D7C4))
        ) {
            Text("Agregar producto", color = Color.White)
        }

        LazyColumn {
            items(lista) { producto ->
                ProductWidget(producto) {
                    list.remove(producto)
                    lista.remove(producto)
                    objectlista.products = list
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTime(
    fechaSeleccionada: LocalDate?,
    onFechaSeleccionada: (LocalDate) -> Unit
) {
    val contexto = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    contexto,
                    { _, year, month, day ->
                        onFechaSeleccionada(LocalDate.of(year, month + 1, day))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    ) {
        OutlinedTextField(
            value = fechaSeleccionada?.toString() ?: "",
            onValueChange = {},
            label = { Text("Seleccionar fecha") },
            readOnly = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ProductWidget(product: Product, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEFCF5))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = product.idProduct),
                    contentDescription = product.name,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(product.name, fontWeight = FontWeight.Bold)
                    Text("Unidades: ${product.amount} ${product.und}")
                }
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = Color.Red,
                modifier = Modifier
                    .clickable { onDelete() }
                    .padding(8.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun ValidateList(list: MarketList): String {
    if (list.name.isBlank()) return "El nombre no puede estar en blanco"
    if (list.date.isBefore(LocalDate.now())) return "La fecha no puede ser anterior a hoy"
    if (list.products.isEmpty()) return "La lista no puede estar vacía"
    return ""
}

