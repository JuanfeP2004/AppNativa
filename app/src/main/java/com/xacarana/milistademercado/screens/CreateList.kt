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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.xacarana.milistademercado.R
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.User
import java.sql.Date
import java.time.LocalDate
import androidx.compose.foundation.lazy.items
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.Product

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateList(navController: NavController, user: User, db: Database, list: MutableList<Product>) {

    var fecha by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    var name by remember { mutableStateOf("Nombre") }
    var description by remember { mutableStateOf("") }
    var lista = remember { mutableStateListOf(*list.toTypedArray()) }
    var messageError by remember { mutableStateOf("") }

    val objectlista: MarketList = MarketList(
        name = name,
        description = description,
        date = fecha!!,
        completion = 0.0f,
        products = list
    )

    Column {

        Row {
            Button(onClick = {
                list.clear()
                navController.navigate("menu")
            }) {
                Text("REGRESAR")
            }
            Button(onClick = {

                val check = ValidateList(objectlista)

                if(check == "") {
                    messageError = ""

                    db.createList(
                        objectlista,
                        user,
                        {
                            list.clear()
                            navController.navigate("menu")
                        },
                        { messageError = it }
                    )


                }
                else
                    messageError = check
            }) {
                Text("CREAR")
            }
        }

        TextField(value = name, onValueChange = {
            name = it
            objectlista.name = name
        })

        Text("Descripción:")
        TextField(value = description, onValueChange = {
            description = it
            objectlista.description
        })

        Row {
            Text("Fecha")
            DateTime(fechaSeleccionada = fecha, onFechaSeleccionada = {
                objectlista.date = fecha!!
                fecha = it
            })
        }

        Text(messageError)

        Box() {
            Column {

                Text("Objetos")
                Button(onClick = {
                    navController.navigate("create-product")
                }) {
                    Text("AGREGAR")
                }
                LazyColumn {
                    items(lista) { elemento ->
                        ProductWidget(
                            elemento = elemento,
                            onDelete = {
                                list.remove(elemento)
                                lista.remove(elemento)
                                objectlista.products == list
                            })
                    }

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
fun ProductWidget(elemento: Product, onDelete: () -> Unit) {
    Box() {
        Row {
            Image(
                painter = painterResource(id = elemento.idProduct),
                contentDescription = elemento.name
            )
            Column {
                Text(elemento.name)
                Text("Unidades: ${elemento.amount}${elemento.und}")
            }
            Icon(
                modifier = Modifier.clickable(onClick = {
                    onDelete()
                }),
                imageVector = Icons.Default.Delete,
                contentDescription = "delete"
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun ValidateList(list: MarketList): String {
    if (list.name == "" || list.name.isNullOrBlank()) return "El nombre no puede estar en blanco"
    if (list.date.isBefore(LocalDate.now())) return "La fecha no puede ser anterior a la actual"
    if (list.products.count() == 0) return "La lista de productos no puede estar vacia"
    return ""
}
