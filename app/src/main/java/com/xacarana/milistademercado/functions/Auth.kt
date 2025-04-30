package com.xacarana.milistademercado.functions

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xacarana.milistademercado.models.MarketList
import com.xacarana.milistademercado.models.User
import kotlinx.coroutines.tasks.await
import kotlin.text.set

class Auth(database: Database) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = database
    private val db = FirebaseFirestore.getInstance()

    var usuario by mutableStateOf(auth.currentUser)
        private set

    fun registrar(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {

        if (name.isEmpty() || email.isEmpty() || password.isEmpty())
            onError("No se pueden dejar campos vacios")
        else
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { tarea ->
                    if (tarea.isSuccessful) {
                        usuario = auth.currentUser

                        val id = usuario?.uid

                        db.collection("Usuarios").document(id!!).set(
                            mapOf(
                                "name" to name
                            )
                        )
                        onSuccess()
                    } else {
                        val mensaje = tarea.exception?.message ?: "Error desconocido"
                        onError(mensaje)
                    }
                }.addOnFailureListener() { exception ->
                    val mensaje = exception?.message ?: "Error desconocido"
                    onError(mensaje)
                }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun iniciarSesion(
        email: String,
        password: String,
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit) {

        if (email.isEmpty() || password.isEmpty())
            onError("No se pueden dejar campos vacios")
        else

            try {
                val auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, password).await()
                usuario = auth.currentUser
                user.ModifyName(getUserName(usuario?.uid!!))
                user.ModifyId(usuario?.uid!!)
                user.ModifyList(getUserList(usuario?.uid!!))
                onSuccess()
            } catch (e: Exception) {
                val mensaje = e?.message ?: "Error desconocido"
                onError(mensaje)
            }
    }

    fun cerrarSesion(
        user: User,
        onSuccess: () -> Unit
    ) {
        user.ModifyId("")
        user.ModifyName("")
        user.ModifyList(mutableListOf())
        auth.signOut()
        usuario = null
        onSuccess()
    }

    suspend fun getUserName(id: String) : String {
        val snapshot = db.collection("Usuarios").document(id).get().await()
        return snapshot.getString("name")!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserList(id: String): MutableList<MarketList> {
        return database.getUserList(
            id = id,
            onFailure = {}
        )
    }
}