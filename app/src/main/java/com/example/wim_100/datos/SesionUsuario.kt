package com.example.wim_100.datos

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.wim_100.modelo.Rol
import com.example.wim_100.modelo.Usuario

object SesionUsuario {
    private val usuarios = mutableListOf(
        Usuario("Ana Pérez", "ana@wim100.cl", "ana12345", Rol.ADMIN),
        Usuario("Luis Soto", "luis@wim100.cl", "luis12345", Rol.NORMAL)
    )

    var usuarioActual by mutableStateOf<Usuario?>(null)
        private set

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
    }

    fun cerrarSesion() {
        usuarioActual = null
    }
}