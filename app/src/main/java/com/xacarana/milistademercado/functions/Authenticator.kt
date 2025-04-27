package com.xacarana.milistademercado.functions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class Authenticator: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var usuario by mutableStateOf(auth.currentUser)
        private set

    fun iniciarSesion(email: String, password: String, onError: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { tarea ->
                if (tarea.isSuccessful) {
                    usuario = auth.currentUser
                } else {
                    onError(tarea.exception?.message ?: "Error desconocido")
                }
            }
    }

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

    fun cerrarSesion() {
        auth.signOut()
        usuario = null
    }

}