package com.xacarana.milistademercado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xacarana.milistademercado.screens.CreateList
import com.xacarana.milistademercado.screens.CreateProduct
import com.xacarana.milistademercado.ui.theme.MiListaDeMercadoTheme
import com.xacarana.milistademercado.screens.Login
import com.xacarana.milistademercado.screens.Menu
import com.xacarana.milistademercado.screens.Register
import com.xacarana.milistademercado.screens.ViewList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register") {
        composable("register") { Register(navController) }
        composable("login") { Login(navController) }
        composable("menu") { Menu(navController) }
        composable("create-list") { CreateList(navController) }
        composable("create-product") { CreateProduct(navController) }
        composable("view-list") { ViewList(navController) }
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