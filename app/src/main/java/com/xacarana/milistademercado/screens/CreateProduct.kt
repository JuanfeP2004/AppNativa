package com.xacarana.milistademercado.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(navController: NavController, user: User, list: MutableList<Product>){

    val opciones = listOf("und", "kg", "lbs", "lts", "cm")
    var seleccion by remember { mutableStateOf("und") }

    var product by remember {  mutableStateOf(Product(
        name = "Tomate",
        amount = 1f,
        und = "und",
        idPhoto = R.drawable.tomate)
    )
    }

    var name = remember { mutableStateOf(product.name) }
    var amount by remember { mutableFloatStateOf(product.amount) }
    var units by remember { mutableStateOf(product.und) }

    Column {

        Row {
            Button(onClick = { navController.navigate("create-list") }) {
                Text("REGRESAR")
            }
            Button(onClick = {
                if(ValidateProduct(product)) {
                    list.add(product)
                    navController.navigate("create-list")
                }
            }) {
                Text("CREAR")
            }
        }

        Text(name.value)

        Row {
            Text("Unidades:")
            TextField(
                modifier = Modifier.fillMaxWidth(0.5f),
                value = amount.toString(),
                onValueChange = {
                    val newAmount = it.toFloatOrNull() // Convierte el texto a Float, o devuelve null si no es válido
                    if (newAmount != null) {
                        amount = newAmount // Solo actualizamos si es un valor válido
                        product.amount = newAmount
                    }
                },
                )
            TextField(
                modifier = Modifier.fillMaxWidth(0.2f),
                value = seleccion,
                readOnly = true,
                onValueChange = {seleccion = it}
            )
            Selector(
                opciones = opciones,
                opcionSeleccionada =  seleccion,
                onOpcionSeleccionada = {
                    product.und = seleccion
                    seleccion = it
                                       },
                product = product
                )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
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
    product: Product
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = opcionSeleccionada,
            onValueChange = { },
            readOnly = true,
            label = { Text("Selecciona una opción") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor() // esto es importante para anclar el menú
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
fun ProductImageWidget(name: String, imageId : Int, product: Product, nameState: MutableState<String>){
    Box(
        modifier = Modifier.clickable(onClick = {
            product.idProduct = imageId
            product.name = name
            nameState.value = name
        })
    ){
        Image(
            painter = painterResource(id = imageId),
            contentDescription = name
        )
        Text(name)
    }
}


fun ValidateProduct(product: Product): Boolean {
    if(product.name == "" || product.name.isNullOrBlank()) return false
    if(product.und == "" || product.und.isNullOrBlank()) return false
    if(product.amount <= 0.0f) return false
    return true
}