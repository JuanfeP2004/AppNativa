package com.xacarana.milistademercado

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.xacarana.milistademercado.functions.Auth
import com.xacarana.milistademercado.functions.Database
import com.xacarana.milistademercado.models.CreateListModel
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.MarketListTrack
import com.xacarana.milistademercado.models.Product
import com.xacarana.milistademercado.models.User
import com.xacarana.milistademercado.models.ViewListModel
import com.xacarana.milistademercado.screens.CreateList
import com.xacarana.milistademercado.screens.CreateProduct
import com.xacarana.milistademercado.screens.DateTime
import com.xacarana.milistademercado.ui.theme.MiListaDeMercadoTheme
import com.xacarana.milistademercado.screens.Login
import com.xacarana.milistademercado.screens.Menu
import com.xacarana.milistademercado.screens.Register
import com.xacarana.milistademercado.screens.ViewList
import java.time.LocalDate
import java.util.Date

val usuario = User(id = "", name = "", mutableListOf())
val firebase = Database()
val authenticator = Auth(firebase)
val list: MutableList<Product> = mutableListOf()

@RequiresApi(Build.VERSION_CODES.O)
val viewlist = ViewListModel(MarketList(
    id = "",
    name = "",
    description = "",
    date = LocalDate.now(),
    completion = 0.0f,
    products = mutableListOf()
))

@RequiresApi(Build.VERSION_CODES.O)
val createlist = CreateListModel(MarketList(
    id = "",
    name = "Nombre",
    description = "",
    date = LocalDate.now(),
    completion = 0.0f,
    products = mutableListOf<Product>() // as SnapshotStateList<Product>
))

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            MiListaDeMercadoTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                }*/
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
        composable("register") { Register(navController, authenticator) }
        composable("login") { Login(navController, usuario, authenticator) }
        composable("menu") { Menu(navController, usuario, firebase, viewlist) }
        composable("create-list") { CreateList(navController, usuario, firebase, list, createlist) }
        composable("create-product") { CreateProduct(navController, usuario, list, createlist) }
        composable("view-list") { ViewList(navController, usuario, viewlist, firebase) }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiListaDeMercadoTheme {
        Greeting("Android")
    }
}