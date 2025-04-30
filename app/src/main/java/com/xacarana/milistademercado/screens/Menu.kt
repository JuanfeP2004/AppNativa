package com.xacarana.milistademercado.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.xacarana.milistademercado.authenticator
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.ViewListModel
import com.xacarana.milistademercado.viewlist
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Menu(navController: NavController, user: User, db: Database, viewmodel: ViewListModel){

    var messageError by remember { mutableStateOf("") }
    //val coroutineScope = rememberCoroutineScope()

    val list: List<MarketList> = user.listas.value!!

    Surface {
        Column {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "configuracion",
                modifier = Modifier.clickable(onClick = {
                    //Codigo temporal, no final
                    authenticator.cerrarSesion(
                        user)
                        {navController.navigate("login")}
                }))
            Text("BIENVENIDO ${user.name.value}")
            Text("Que quieres comprar hoy?")

            Button(onClick = { navController.navigate("create-list") }) {
                Text("CREAR LISTA")
            }

            Text("MIS LISTAS")
            Text(messageError)

            LazyColumn {
                items(list) { elemento ->
                    ListWidget(navController, elemento)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListWidget(navController: NavController, list: MarketList){
    Box(
        modifier = Modifier.clickable {
            viewlist.ModifyList(list)
            navController.navigate("view-list")
        }
    ){
        Box(){}
        Column {
            Text(list.name)
            Row {
                Column {
                    Text("Para: ${list.date.toString()}")
                    Text("Objetos: ${list.products.count()}")
                }
                Column {
                    Text("Completado:")
                    Text("${list.completion}%")
                }
            }
        }
    }
}