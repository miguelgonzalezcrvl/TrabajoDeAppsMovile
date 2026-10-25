package com.example.wim_100.datos

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.wim_100.modelo.Usuario

object SesionUsuario {
    private val usuarios = mutableListOf<Usuario>()

    var usuarioActual by mutableStateOf<Usuario?>(null)
        private set

    fun cargar(guardados: List<Usuario>) {
        usuarios.clear()
        usuarios.addAll(guardados)
    }

    fun iniciarSesion(correo: String, contrasena: String): Boolean {
        val usuario = usuarios.find {
            it.correo.equals(correo.trim(), ignoreCase = true) && it.contrasena == contrasena
        }
        usuarioActual = usuario
        return usuario != null
    }

    fun actualizarFoto(fotoUri: Uri?) {
        val actual = usuarioActual ?: return
        val actualizado = actual.copy(fotoUri = fotoUri)
        val indice = usuarios.indexOfFirst { it.correo == actual.correo }
        if (indice >= 0) usuarios[indice] = actualizado
        usuarioActual = actualizado
        if (actual.fotoUri != fotoUri) Almacen.borrarFoto(actual.fotoUri)
        Almacen.guardarUsuarios(usuarios.toList())
    }

    fun cerrarSesion() {
        usuarioActual = null
    }
}