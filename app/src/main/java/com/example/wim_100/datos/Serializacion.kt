package com.example.wim_100.datos

import com.example.wim_100.modelo.AccionHistorial
import com.example.wim_100.modelo.Criterio
import com.example.wim_100.modelo.EstadoPostulante
import com.example.wim_100.modelo.Estudio
import com.example.wim_100.modelo.Postulante
import com.example.wim_100.modelo.RegistroHistorial
import com.example.wim_100.modelo.Rol
import com.example.wim_100.modelo.Usuario
import org.json.JSONArray
import org.json.JSONObject

internal fun <T> JSONArray.mapear(convertir: (JSONObject) -> T): List<T> =
    List(length()) { convertir(getJSONObject(it)) }

private fun JSONObject.textoONulo(clave: String): String? =
    if (has(clave) && !isNull(clave)) getString(clave) else null

// ---------- Usuario ----------

internal fun Usuario.aJson(): JSONObject = JSONObject()
    .put("nombre", nombre)
    .put("correo", correo)
    .put("contrasena", contrasena)
    .put("rol", rol.name)
    .put("foto", Almacen.nombreDeFoto(fotoUri))

internal fun usuarioDesdeJson(o: JSONObject) = Usuario(
    nombre = o.getString("nombre"),
    correo = o.getString("correo"),
    contrasena = o.getString("contrasena"),
    rol = Rol.valueOf(o.getString("rol")),
    fotoUri = Almacen.uriDeFoto(o.textoONulo("foto"))
)

// ---------- Postulante ----------

private fun Estudio.aJson(): JSONObject = JSONObject()
    .put("institucion", institucion)
    .put("titulo", titulo)
    .put("anioTermino", anioTermino)

private fun estudioDesdeJson(o: JSONObject) = Estudio(
    institucion = o.getString("institucion"),
    titulo = o.getString("titulo"),
    anioTermino = o.getString("anioTermino")
)

internal fun Postulante.aJson(): JSONObject = JSONObject()
    .put("id", id)
    .put("nombre", nombre)
    .put("edad", edad)
    .put("descripcion", descripcion)
    .put("estudios", JSONArray(estudios.map { it.aJson() }))
    .put("telefono", telefono)
    .put("correo", correo)
    .put("comuna", comuna)
    .put("nivelIngles", nivelIngles)
    .put("foto", Almacen.nombreDeFoto(fotoUri))
    .put("fechaIngreso", fechaIngreso)
    .put("estado", estado.name)

internal fun postulanteDesdeJson(o: JSONObject) = Postulante(
    id = o.getInt("id"),
    nombre = o.getString("nombre"),
    edad = o.getInt("edad"),
    descripcion = o.getString("descripcion"),
    estudios = o.getJSONArray("estudios").mapear(::estudioDesdeJson),
    telefono = o.getString("telefono"),
    correo = o.getString("correo"),
    comuna = o.getString("comuna"),
    nivelIngles = o.getString("nivelIngles"),
    fotoUri = Almacen.uriDeFoto(o.textoONulo("foto")),
    fechaIngreso = o.getString("fechaIngreso"),
    estado = EstadoPostulante.valueOf(o.getString("estado"))
)

// ---------- Historial (guarda una copia del postulante tal como estaba) ----------

internal fun RegistroHistorial.aJson(): JSONObject = JSONObject()
    .put("postulante", postulante.aJson())
    .put("usuario", usuario)
    .put("fecha", fecha)
    .put("accion", accion.name)

internal fun registroDesdeJson(o: JSONObject) = RegistroHistorial(
    postulante = postulanteDesdeJson(o.getJSONObject("postulante")),
    usuario = o.getString("usuario"),
    fecha = o.getString("fecha"),
    accion = AccionHistorial.valueOf(o.getString("accion"))
)

// ---------- Criterio ----------

internal fun Criterio.aJson(): JSONObject = JSONObject()
    .put("definicion", definicion)
    .put("puntos", puntos)

internal fun criterioDesdeJson(o: JSONObject) = Criterio(
    definicion = o.getString("definicion"),
    puntos = o.getInt("puntos")
)
