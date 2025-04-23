package com.xacarana.milistademercado.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(navController: NavController){

    val opciones = listOf("und", "kg", "lbs", "lts", "cm")
    var seleccion by remember { mutableStateOf("und") }

    Column {

        Row {
            Button(onClick = { navController.navigate("create-list") }) {
                Text("REGRESAR")
            }
            Button(onClick = { navController.navigate("create-list") }) {
                Text("CREAR")
            }
        }

        Text("Nombre del objeto")

        Row {
            Text("Unidades:")
            TextField(value = "", onValueChange = {})
            Selector(opciones = opciones, opcionSeleccionada =  seleccion, onOpcionSeleccionada = {seleccion = it})
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4)
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Selector(
    opciones: List<String>,
    opcionSeleccionada: String,
    onOpcionSeleccionada: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = opcionSeleccionada,
            onValueChange = {},
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
                        expanded = false
                    }
                )
            }
        }
    }
}