package com.example.wim_100.datos

import androidx.compose.runtime.mutableStateListOf
import com.example.wim_100.modelo.AccionHistorial
import com.example.wim_100.modelo.Postulante
import com.example.wim_100.modelo.RegistroHistorial

object HistorialEnMemoria {
    val registros = mutableStateListOf<RegistroHistorial>()

    fun cargar(guardados: List<RegistroHistorial>) {
        registros.clear()
        registros.addAll(guardados)
    }

    fun registrar(postulante: Postulante, accion: AccionHistorial) {
        val usuario = SesionUsuario.usuarioActual?.nombre ?: "Desconocido"
        registros.add(0, RegistroHistorial(postulante, usuario, fechaDeHoy(), accion))
        Almacen.guardarHistorial(registros.toList())
    }
}