package com.example.wim_100.modelo

import java.util.Locale

const val PORCENTAJE_APROBACION = 60.0

enum class Cumplimiento(val etiqueta: String, val factor: Double) {
    CUMPLE("Cumple", 1.0),
    MEDIANAMENTE("Cumple medianamente", 0.5),
    NO_CUMPLE("No cumple", 0.0)
}

data class ResultadoEvaluacion(val puntajeObtenido: Double, val puntajeTotal: Int) {
    val porcentaje: Double
        get() = if (puntajeTotal == 0) 0.0 else puntajeObtenido / puntajeTotal * 100.0
    val aprobado: Boolean
        get() = porcentaje >= PORCENTAJE_APROBACION
}

fun calcularResultado(criterios: List<Criterio>, respuestas: List<Cumplimiento>): ResultadoEvaluacion {
    val obtenido = criterios.indices.sumOf { criterios[it].puntos * respuestas[it].factor }
    return ResultadoEvaluacion(obtenido, criterios.sumOf { it.puntos })
}

// 17.0 -> "17", 17.5 -> "17,5"
fun Double.comoPuntaje(): String =
    if (this % 1.0 == 0.0) toInt().toString()
    else String.format(Locale.forLanguageTag("es-CL"), "%.1f", this)