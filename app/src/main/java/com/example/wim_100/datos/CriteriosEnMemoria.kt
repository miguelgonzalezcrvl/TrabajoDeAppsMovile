package com.example.wim_100.datos

import androidx.compose.runtime.mutableStateListOf
import com.example.wim_100.modelo.Criterio

object CriteriosEnMemoria {
    val criterios = mutableStateListOf<Criterio>()

    fun cargar(guardados: List<Criterio>) {
        criterios.clear()
        criterios.addAll(guardados)
    }

    fun reemplazar(nuevos: List<Criterio>) {
        criterios.clear()
        criterios.addAll(nuevos)
        Almacen.guardarCriterios(criterios.toList())
    }
}