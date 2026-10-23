package com.example.wim_100.modelo

enum class AccionHistorial(val etiqueta: String) {
    AGREGADO("Agregado"),
    APROBADO("Aprobado"),
    NO_APROBADO("Rechazado")
}

data class RegistroHistorial(
    val postulante: Postulante,
    val usuario: String,
    val fecha: String,
    val accion: AccionHistorial
)