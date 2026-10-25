package com.example.wim_100.datos

import androidx.compose.runtime.mutableStateListOf
import com.example.wim_100.modelo.EstadoPostulante
import com.example.wim_100.modelo.Postulante

object PostulantesEnMemoria {
    val postulantes = mutableStateListOf<Postulante>()

    private var siguienteId = 1

    fun cargar(guardados: List<Postulante>) {
        postulantes.clear()
        postulantes.addAll(guardados)
        siguienteId = (guardados.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun buscar(id: Int): Postulante? = postulantes.find { it.id == id }

    /** Le asigna id y fecha de hoy, la guarda y devuelve el postulante ya guardado. */
    fun agregar(postulante: Postulante): Postulante {
        val nuevo = postulante.copy(id = siguienteId++, fechaIngreso = fechaDeHoy())
        postulantes.add(0, nuevo)
        Almacen.guardarPostulantes(postulantes.toList())
        return nuevo
    }

    fun actualizarEstado(id: Int, estado: EstadoPostulante) {
        val indice = postulantes.indexOfFirst { it.id == id }
        if (indice >= 0) {
            postulantes[indice] = postulantes[indice].copy(estado = estado)
            Almacen.guardarPostulantes(postulantes.toList())
        }
    }
}