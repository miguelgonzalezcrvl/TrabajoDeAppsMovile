package com.example.wim_100.modelo

import android.net.Uri

enum class Rol(val etiqueta: String) {
    NORMAL("Evaluador"),
    ADMIN("Administrador")
}

data class Usuario(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rol: Rol = Rol.NORMAL,
    val fotoUri: Uri? = null
)