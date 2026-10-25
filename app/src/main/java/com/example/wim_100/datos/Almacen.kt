package com.example.wim_100.datos

import android.content.Context
import android.net.Uri
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID

/**
 * Guarda y carga los datos de la app en archivos JSON dentro del almacenamiento
 * interno (filesDir). Se llama una sola vez desde MainActivity.
 *
 * - Primer arranque: copia assets/datos_prueba.json a los archivos guardados.
 * - Siguientes arranques: solo lee lo guardado (los datos de prueba ya no se usan).
 * - Para volver a los datos de prueba: borrar datos de la app o llamar a restablecer().
 */
object Almacen {
    private const val SEMILLA = "datos_prueba.json"
    private const val USUARIOS = "usuarios.json"
    private const val POSTULANTES = "postulantes.json"
    private const val HISTORIAL = "historial.json"
    private const val CRITERIOS = "criterios.json"

    private lateinit var contexto: Context

    fun iniciar(context: Context) {
        contexto = context.applicationContext
        if (!archivo(USUARIOS).exists()) sembrar()
        try {
            cargarTodo()
        } catch (e: Exception) {
            // Archivos dañados: se vuelve a los datos de prueba
            sembrar()
            cargarTodo()
        }
    }

    /** Borra lo guardado y vuelve a copiar los datos de prueba. */
    fun restablecer() {
        sembrar()
        cargarTodo()
    }

    // ---------- Guardar ----------

    fun guardarUsuarios(lista: List<com.example.wim_100.modelo.Usuario>) =
        escribir(USUARIOS, lista.map { it.aJson() })

    fun guardarPostulantes(lista: List<com.example.wim_100.modelo.Postulante>) =
        escribir(POSTULANTES, lista.map { it.aJson() })

    fun guardarHistorial(lista: List<com.example.wim_100.modelo.RegistroHistorial>) =
        escribir(HISTORIAL, lista.map { it.aJson() })

    fun guardarCriterios(lista: List<com.example.wim_100.modelo.Criterio>) =
        escribir(CRITERIOS, lista.map { it.aJson() })

    // ---------- Fotos ----------

    /** Copia la foto elegida a la carpeta de la app, porque el permiso de la galería es temporal. */
    fun copiarFoto(origen: Uri): Uri? {
        return try {
            val destino = File(carpetaFotos(), "${UUID.randomUUID()}.jpg")
            val entrada = contexto.contentResolver.openInputStream(origen) ?: return null
            entrada.use { fuente ->
                destino.outputStream().use { salida -> fuente.copyTo(salida) }
            }
            Uri.fromFile(destino)
        } catch (e: Exception) {
            null
        }
    }

    fun borrarFoto(uri: Uri?) {
        if (uri?.scheme == "file") uri.path?.let { File(it).delete() }
    }

    internal fun nombreDeFoto(uri: Uri?): String? =
        if (uri?.scheme == "file") uri.lastPathSegment else null

    internal fun uriDeFoto(nombre: String?): Uri? =
        nombre?.let { File(carpetaFotos(), it) }?.takeIf { it.exists() }?.let { Uri.fromFile(it) }

    private fun carpetaFotos() = File(contexto.filesDir, "fotos").apply { mkdirs() }

    // ---------- Internos ----------

    private fun cargarTodo() {
        SesionUsuario.cargar(leer(USUARIOS).mapear(::usuarioDesdeJson))
        PostulantesEnMemoria.cargar(leer(POSTULANTES).mapear(::postulanteDesdeJson))
        HistorialEnMemoria.cargar(leer(HISTORIAL).mapear(::registroDesdeJson))
        CriteriosEnMemoria.cargar(leer(CRITERIOS).mapear(::criterioDesdeJson))
    }

    private fun sembrar() {
        val texto = contexto.assets.open(SEMILLA).bufferedReader().use { it.readText() }
        val semilla = JSONObject(texto)
        val postulantes = semilla.getJSONArray("postulantes")
        val historial = semilla.getJSONArray("historial")
        // En los datos de prueba el historial apunta al postulante por su id; aquí se expande
        for (i in 0 until historial.length()) {
            val registro = historial.getJSONObject(i)
            val id = registro.getInt("postulanteId")
            registro.remove("postulanteId")
            registro.put("postulante", postulantes.mapear { it }.first { it.getInt("id") == id })
        }
        escribirTexto(USUARIOS, semilla.getJSONArray("usuarios").toString())
        escribirTexto(POSTULANTES, postulantes.toString())
        escribirTexto(HISTORIAL, historial.toString())
        escribirTexto(CRITERIOS, semilla.getJSONArray("criterios").toString())
    }

    private fun archivo(nombre: String) = File(contexto.filesDir, nombre)

    private fun leer(nombre: String) = JSONArray(archivo(nombre).readText())

    private fun escribir(nombre: String, objetos: List<JSONObject>) {
        if (!::contexto.isInitialized) return // por ejemplo en un @Preview
        escribirTexto(nombre, JSONArray(objetos).toString())
    }

    // Se escribe a un archivo temporal y luego se renombra para no dejar un archivo a medias
    private fun escribirTexto(nombre: String, texto: String) {
        val destino = archivo(nombre)
        val temporal = archivo("$nombre.tmp")
        temporal.writeText(texto)
        if (!temporal.renameTo(destino)) {
            destino.writeText(texto)
            temporal.delete()
        }
    }
}
