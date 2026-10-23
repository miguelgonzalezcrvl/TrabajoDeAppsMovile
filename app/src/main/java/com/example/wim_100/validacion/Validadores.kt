package com.example.wim_100.validacion

import android.util.Patterns

fun validarCorreo(correo: String): String? = when {
    correo.isBlank() -> "Ingresa tu correo"
    !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> "El correo no es válido"
    else -> null
}

fun validarContrasena(contrasena: String): String? = when {
    contrasena.isBlank() -> "Ingresa tu contraseña"
    contrasena.length < 6 -> "Debe tener al menos 6 caracteres"
    else -> null
}