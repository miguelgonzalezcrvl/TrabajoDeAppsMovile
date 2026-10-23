package com.example.wim_100

object Rutas {
    const val LOGIN = "login"
    const val MENU = "menu"
    const val AGREGAR = "agregar"
    const val EDITAR_CRITERIOS = "editar_criterios"
    const val EVALUAR = "evaluar/{id}"
    const val RESULTADO = "resultado/{id}"

    fun evaluar(id: Int) = "evaluar/$id"
    fun resultado(id: Int) = "resultado/$id"
}