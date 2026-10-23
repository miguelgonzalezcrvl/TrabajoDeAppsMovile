package com.example.wim_100.datos

import androidx.compose.runtime.mutableStateListOf
import com.example.wim_100.modelo.Criterio

object CriteriosEnMemoria {
    val criterios = mutableStateListOf(
        Criterio("Mínimo 3 años de experiencia en cargos similares", 7),
        Criterio("Disponibilidad para trabajar en Santiago o modalidad híbrida", 6),
        Criterio("Nivel de inglés intermedio o superior", 5),
        Criterio("Título profesional o técnico en el área exigida", 5),
        Criterio("Manejo de herramientas del rubro (Figma o equivalente)", 4),
        Criterio("Certificaciones vigentes y comprobadas", 2)
    )

    fun reemplazar(nuevos: List<Criterio>) {
        criterios.clear()
        criterios.addAll(nuevos)
    }
}