package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.models.CreateListModel
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.Product

val GreenPrimary = Color(0xFF2ECC71)
val GreenSecondary = Color(0xFF7EECA5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(
    navController: NavController,
    user: User,
    list: MutableList<Product>,
    createlist: CreateListModel
) {
    val opciones = listOf("und", "kg", "lbs", "lts", "cm")
    var seleccion by remember { mutableStateOf("und") }
    var errorMessage by remember { mutableStateOf("") }

    var product by remember {
        mutableStateOf(
            Product(
                id = "",
                name = "Tomate",
                amount = 1f,
                und = "und",
                idPhoto = R.drawable.tomate,
                check = "none"
            )
        )
    }

    var name = remember { mutableStateOf(product.name) }
    var amount by remember { mutableFloatStateOf(product.amount) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Agregar Producto",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate("create-list") },
                colors = ButtonDefaults.buttonColors(containerColor = GreenSecondary),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Regresar", color = Color.White)
            }
            Button(
                onClick = {
                    if (ValidateProduct(product)) {
                        list.add(product)
                        errorMessage = ""
                        navController.navigate("create-list")
                    } else {
                        errorMessage = "Completa todos los campos correctamente"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("Agregar", color = Color.White)
            }
        }

        if (errorMessage.isNotBlank()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Text(
            text = "Producto: ${name.value}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = amount.toString(),
                onValueChange = {
                    val newAmount = it.toFloatOrNull()
                    if (newAmount != null) {
                        amount = newAmount
                        product.amount = newAmount
                    }
                },
                label = { Text("Cantidad") }
            )

            Selector(
                opciones = opciones,
                opcionSeleccionada = seleccion,
                onOpcionSeleccionada = {
                    product.und = it
                    seleccion = it
                },
                product = product,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Selecciona una imagen:",
            fontWeight = FontWeight.Medium
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxHeight(0.5f)
        ) {
            item { ProductImageWidget("Tomate", R.drawable.tomate, product, name) }
            item { ProductImageWidget("Pollo", R.drawable.pollo, product, name) }
            item { ProductImageWidget("Carne", R.drawable.carne, product, name) }
            item { ProductImageWidget("Platano", R.drawable.platano, product, name) }
            item { ProductImageWidget("Pescado", R.drawable.pescado, product, name) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Selector(
    opciones: List<String>,
    opcionSeleccionada: String,
    onOpcionSeleccionada: (String) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = opcionSeleccionada,
            onValueChange = { },
            readOnly = true,
            label = { Text("Unidad") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onOpcionSeleccionada(opcion)
                        product.und = opcion
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProductImageWidget(
    name: String,
    imageId: Int,
    product: Product,
    nameState: MutableState<String>
) {
    val isSelected = product.name == name

    Column(
        modifier = Modifier
            .clickable {
                product.idProduct = imageId
                product.name = name
                nameState.value = name
            }
            .background(if (isSelected) GreenSecondary.copy(alpha = 0.2f) else Color.Transparent)
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = name,
            modifier = Modifier.size(64.dp)
        )
        Text(text = name, fontSize = 14.sp)
    }
}

fun ValidateProduct(product: Product): Boolean {
    return product.name.isNotBlank() && product.und.isNotBlank() && product.amount > 0.0f
}

