package com.example.wim_100.datos

import androidx.compose.runtime.mutableStateListOf
import com.example.wim_100.modelo.AccionHistorial
import com.example.wim_100.modelo.Postulante
import com.example.wim_100.modelo.RegistroHistorial

object HistorialEnMemoria {
    private fun p(id: Int) = PostulantesEnMemoria.postulantes.first { it.id == id }

    val registros = mutableStateListOf(
        RegistroHistorial(p(5), "Ana Pérez", "20-10-2026", AccionHistorial.APROBADO),
        RegistroHistorial(p(6), "Luis Soto", "19-10-2026", AccionHistorial.NO_APROBADO),
        RegistroHistorial(p(7), "Luis Soto", "18-10-2026", AccionHistorial.AGREGADO),
        RegistroHistorial(p(4), "Ana Pérez", "17-10-2026", AccionHistorial.AGREGADO),
        RegistroHistorial(p(3), "Ana Pérez", "16-10-2026", AccionHistorial.AGREGADO),
        RegistroHistorial(p(2), "Luis Soto", "15-10-2026", AccionHistorial.AGREGADO),
        RegistroHistorial(p(1), "Luis Soto", "14-10-2026", AccionHistorial.AGREGADO),
        RegistroHistorial(p(6), "Ana Pérez", "13-10-2026", AccionHistorial.AGREGADO),
        RegistroHistorial(p(5), "Luis Soto", "12-10-2026", AccionHistorial.AGREGADO)
    )

    fun registrar(postulante: Postulante, accion: AccionHistorial) {
        val usuario = SesionUsuario.usuarioActual?.nombre ?: "Desconocido"
        registros.add(0, RegistroHistorial(postulante, usuario, fechaDeHoy(), accion))
    }
}