package com.example.wim_100.datos

import androidx.compose.runtime.mutableStateListOf
import com.example.wim_100.modelo.EstadoPostulante
import com.example.wim_100.modelo.Estudio
import com.example.wim_100.modelo.Postulante

object PostulantesEnMemoria {
    val postulantes = mutableStateListOf(
        Postulante(
            id = 1, nombre = "Camila Rojas Fuentes", edad = 24,
            descripcion = "Diseñadora gráfica con 2 años de experiencia en branding, diseño de interfaces y estrategia visual para marcas de retail y tecnología.",
            estudios = listOf(
                Estudio("Universidad de Chile", "Diseño Gráfico", "2025"),
                Estudio("Duoc UC", "Diplomado en Experiencia de Usuario", "2026")
            ),
            telefono = "+56 9 1234 5678", correo = "camila.rojas@ejemplo.com",
            comuna = "Santiago", nivelIngles = "Avanzado", fechaIngreso = "14-10-2026"
        ),
        Postulante(
            id = 2, nombre = "Martina Vega Torres", edad = 29,
            descripcion = "Desarrolladora Android con 5 años de experiencia en aplicaciones móviles para banca y retail. Trabaja con Kotlin y Jetpack Compose.",
            estudios = listOf(Estudio("Universidad de Santiago", "Ingeniería en Informática", "2019")),
            telefono = "+56 9 8765 4321", correo = "martina.vega@ejemplo.com",
            comuna = "Ñuñoa", nivelIngles = "Avanzado", fechaIngreso = "15-10-2026"
        ),
        Postulante(
            id = 3, nombre = "Javiera Núñez Alarcón", edad = 31,
            descripcion = "Analista de datos con experiencia en reportería, SQL y visualización para el sector salud.",
            estudios = listOf(
                Estudio("Pontificia Universidad Católica", "Ingeniería Civil Industrial", "2017"),
                Estudio("Universidad de Chile", "Magíster en Ciencia de Datos", "2021")
            ),
            telefono = "+56 9 5555 1212", correo = "javiera.nunez@ejemplo.com",
            comuna = "Providencia", nivelIngles = "Intermedio", fechaIngreso = "16-10-2026"
        ),
        Postulante(
            id = 4, nombre = "Sofía Mora Díaz", edad = 27,
            descripcion = "Técnica en soporte TI con 4 años atendiendo mesas de ayuda y administrando redes de oficinas pequeñas.",
            estudios = listOf(Estudio("Inacap", "Técnico en Informática", "2021")),
            telefono = "+56 9 4444 3030", correo = "sofia.mora@ejemplo.com",
            comuna = "Maipú", nivelIngles = "Básico", fechaIngreso = "17-10-2026"
        ),
        Postulante(
            id = 5, nombre = "Daniela Pizarro Soto", edad = 35,
            descripcion = "Jefa de proyectos con 10 años liderando equipos de desarrollo bajo metodologías ágiles.",
            estudios = listOf(
                Estudio("Universidad Adolfo Ibáñez", "Ingeniería Comercial", "2013"),
                Estudio("Duoc UC", "Diplomado en Gestión de Proyectos", "2016")
            ),
            telefono = "+56 9 7777 8888", correo = "daniela.pizarro@ejemplo.com",
            comuna = "Las Condes", nivelIngles = "Avanzado", fechaIngreso = "12-10-2026",
            estado = EstadoPostulante.APROBADO
        ),
        Postulante(
            id = 6, nombre = "Fernanda Contreras Lagos", edad = 22,
            descripcion = "Egresada reciente de diseño con experiencia en prácticas y proyectos académicos.",
            estudios = listOf(Estudio("Duoc UC", "Diseño Digital", "2026")),
            telefono = "+56 9 3333 9090", correo = "fernanda.contreras@ejemplo.com",
            comuna = "La Florida", nivelIngles = "Básico", fechaIngreso = "13-10-2026",
            estado = EstadoPostulante.RECHAZADO
        ),
        Postulante(
            id = 7, nombre = "Valentina Araya Ríos", edad = 26,
            descripcion = "Ingeniera en sistemas con experiencia en pruebas de software y automatización de QA.",
            estudios = listOf(Estudio("Universidad Tecnológica Metropolitana", "Ingeniería en Informática", "2022")),
            telefono = "+56 9 2222 6060", correo = "valentina.araya@ejemplo.com",
            comuna = "Santiago", nivelIngles = "Intermedio", fechaIngreso = "18-10-2026"
        )
    )

    private var siguienteId = 8

    fun buscar(id: Int): Postulante? = postulantes.find { it.id == id }

    /** Le asigna id y fecha de hoy, la guarda y devuelve el postulante ya guardado. */
    fun agregar(postulante: Postulante): Postulante {
        val nuevo = postulante.copy(id = siguienteId++, fechaIngreso = fechaDeHoy())
        postulantes.add(0, nuevo)
        return nuevo
    }

    fun actualizarEstado(id: Int, estado: EstadoPostulante) {
        val indice = postulantes.indexOfFirst { it.id == id }
        if (indice >= 0) postulantes[indice] = postulantes[indice].copy(estado = estado)
    }
}