package com.xacarana.milistademercado.screens

import android.icu.util.Calendar
import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
//import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.models.User
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateList(navController: NavController, user: User){

    var fecha by remember { mutableStateOf<LocalDate?>(null) }

    Column {

        Row {
            Button(onClick = { navController.navigate("menu") }) {
                Text("REGRESAR")
            }
            Button(onClick = { navController.navigate("menu") }) {
                Text("CREAR")
            }
        }

        TextField(value = "Nombre de la lista", onValueChange = {})

        Text("Descripción:")

        TextField(value = "", onValueChange = {})

        Row {
            Text("Fecha")
            DateTime(fechaSeleccionada = fecha, onFechaSeleccionada = { fecha = it})
            //DatePicker(datepicker)

        }

        Box() {
            Column {

                Text("Objetos")
                Button(onClick = { navController.navigate("create-product") }) {
                    Text("AGREGAR")
                }
                LazyColumn {
                    item {
                        ProductWidget()
                    }
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeA(
    fechaSeleccionada: LocalDate?,
    onFechaSeleccionada: (LocalDate) -> Unit
) {
    val contexto = LocalContext.current
    OutlinedTextField(
        value = fechaSeleccionada?.toString() ?: "",
        onValueChange = {},
        label = { Text("Seleccionar fecha") },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val calendario = Calendar.getInstance()
                val year = calendario.get(Calendar.YEAR)
                val month = calendario.get(Calendar.MONTH)
                val day = calendario.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    contexto,
                    { _, anio, mes, dia ->
                        val fecha = LocalDate.of(anio, mes + 1, dia)
                        onFechaSeleccionada(fecha)
                    },
                    year,
                    month,
                    day
                ).show()
            },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Seleccionar fecha"
            )
        }
    )
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
                val calendario = Calendar.getInstance()
                val year = calendario.get(Calendar.YEAR)
                val month = calendario.get(Calendar.MONTH)
                val day = calendario.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    contexto,
                    { _, anio, mes, dia ->
                        val fecha = LocalDate.of(anio, mes + 1, dia)
                        onFechaSeleccionada(fecha)
                    },
                    year,
                    month,
                    day
                ).show()
            }
    ) {
        OutlinedTextField(
            value = fechaSeleccionada?.toString() ?: "",
            onValueChange = {},
            label = { Text("Seleccionar fecha") },
            readOnly = true,
            enabled = false, // bloquea el input para evitar que el usuario intente escribir
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ProductWidget() {
    Box(){
        Row {
            Image(painter = painterResource(id = R.drawable.tomate), contentDescription = "tomate")
            Column {
                Text("Tomates")
                Text("Unidades: 20kg")
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete"
            )
        }
    }
}


