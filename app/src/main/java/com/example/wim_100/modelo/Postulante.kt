package com.example.wim_100.modelo

import android.net.Uri

enum class EstadoPostulante { PENDIENTE, APROBADO, RECHAZADO }

data class Estudio(
    val institucion: String,
    val titulo: String,
    val anioTermino: String
)

data class Postulante(
    val id: Int,
    val nombre: String,
    val edad: Int,
    val descripcion: String,
    val estudios: List<Estudio>,
    val telefono: String,
    val correo: String,
    val comuna: String,
    val nivelIngles: String,
    val fotoUri: Uri? = null,
    val fechaIngreso: String = "",
    val estado: EstadoPostulante = EstadoPostulante.PENDIENTE
)