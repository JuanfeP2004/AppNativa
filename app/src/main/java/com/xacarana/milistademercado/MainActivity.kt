package com.xacarana.milistademercado

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.*
import com.xacarana.milistademercado.screens.*
import com.xacarana.milistademercado.ui.theme.MiListaDeMercadoTheme
import java.time.LocalDate

val usuario = User(id = "", name = "", mutableListOf())
val firebase = Database()
val authenticator = Auth(firebase)
val list: MutableList<Product> = mutableListOf()

@RequiresApi(Build.VERSION_CODES.O)
val viewlist = ViewListModel(
    MarketList(
        id = "",
        name = "",
        description = "",
        date = LocalDate.now(),
        completion = 0.0f,
        products = mutableListOf()
    )
)

@RequiresApi(Build.VERSION_CODES.O)
val createlist = CreateListModel(
    MarketList(
        id = "",
        name = "Nombre",
        description = "",
        date = LocalDate.now(),
        completion = 0.0f,
        products = mutableListOf()
    )
)

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            MiListaDeMercadoTheme {
                AppNavigator()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register") {
        composable("register") {
            Register(navController, authenticator)
        }
        composable("login") {
            Login(navController, usuario, authenticator)
        }
        composable("menu") {
            Menu(navController, usuario, firebase, viewlist)
        }
        composable("create-list") {
            CreateList(navController, usuario, firebase, list, createlist)
        }
        composable("create-product") {
            CreateProduct(navController, usuario, list, createlist)
        }
        composable("view-list") {
            ViewList(navController, viewlist, firebase)
        }
        composable("settings") {
            SettingsScreen(navController, usuario, authenticator)
        }
    }
}


