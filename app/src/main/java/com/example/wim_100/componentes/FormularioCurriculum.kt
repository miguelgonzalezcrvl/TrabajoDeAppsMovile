package com.example.wim_100.componentes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

class EstudioState {
    var institucion by mutableStateOf("")
    var titulo by mutableStateOf("")
    var anioTermino by mutableStateOf("")
}

class FormularioCurriculumState {
    var nombre by mutableStateOf("")
    var edad by mutableStateOf("")
    var descripcion by mutableStateOf("")
    val estudios = mutableStateListOf(EstudioState())
    var telefono by mutableStateOf("")
    var correo by mutableStateOf("")
    var comuna by mutableStateOf("")
    var nivelIngles by mutableStateOf("")
}

private val nivelesIngles = listOf("Sin conocimientos", "Básico", "Intermedio", "Avanzado", "Nativo")

@Composable
fun FormularioCurriculum(
    state: FormularioCurriculumState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TarjetaDatosPersonales(state)
        TarjetaDescripcion(state)
        TarjetaEstudios(state)
        TarjetaContacto(state)
        TarjetaIngles(state)
    }
}

/** Contenedor con el mismo estilo de borde que TarjetaCurriculum. */
@Composable
private fun TarjetaSeccion(
    titulo: String? = null,
    icono: ImageVector? = null,
    contenido: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (titulo != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (icono != null) {
                        Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            contenido()
        }
    }
}

// 1) Icono de persona + nombre + edad
@Composable
private fun TarjetaDatosPersonales(state: FormularioCurriculumState) {
    TarjetaSeccion {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AvatarPersona(tamano = 90.dp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = { state.nombre = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.edad,
                    onValueChange = { nuevo ->
                        if (nuevo.length <= 2 && nuevo.all { it.isDigit() }) state.edad = nuevo
                    },
                    label = { Text("Edad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// 2) Descripción
@Composable
private fun TarjetaDescripcion(state: FormularioCurriculumState) {
    TarjetaSeccion(titulo = "Descripción", icono = Icons.Default.Info) {
        OutlinedTextField(
            value = state.descripcion,
            onValueChange = { state.descripcion = it },
            placeholder = { Text("Cuenta brevemente sobre ti") },
            minLines = 3,
            maxLines = 6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// 3) Estudios: cajas que se pueden expandir/contraer y agregar más
@Composable
private fun TarjetaEstudios(state: FormularioCurriculumState) {
    TarjetaSeccion(titulo = "Estudios", icono = Icons.Default.Star) {
        state.estudios.forEachIndexed { indice, estudio ->
            key(estudio) {
                CajaEstudio(
                    numero = indice + 1,
                    estudio = estudio,
                    puedeEliminar = state.estudios.size > 1,
                    onEliminar = { state.estudios.remove(estudio) }
                )
            }
        }
        OutlinedButton(
            onClick = { state.estudios.add(EstudioState()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Agregar otro estudio")
        }
    }
}

@Composable
private fun CajaEstudio(
    numero: Int,
    estudio: EstudioState,
    puedeEliminar: Boolean,
    onEliminar: () -> Unit
) {
    var expandido by rememberSaveable { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFDBDAE5)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F5FA))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Cabecera: toca para expandir o contraer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandido = !expandido },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Estudio $numero",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    if (!expandido && estudio.institucion.isNotBlank()) {
                        Text(
                            text = estudio.institucion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (puedeEliminar) {
                    IconButton(onClick = onEliminar) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar estudio")
                    }
                }
                Icon(
                    imageVector = if (expandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expandido) "Contraer" else "Expandir"
                )
            }

            AnimatedVisibility(visible = expandido) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = estudio.institucion,
                        onValueChange = { estudio.institucion = it },
                        label = { Text("Institución") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = estudio.titulo,
                        onValueChange = { estudio.titulo = it },
                        label = { Text("Carrera o título") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = estudio.anioTermino,
                        onValueChange = { nuevo ->
                            if (nuevo.length <= 4 && nuevo.all { it.isDigit() }) estudio.anioTermino = nuevo
                        },
                        label = { Text("Año de término") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// 4) Contacto + comuna
@Composable
private fun TarjetaContacto(state: FormularioCurriculumState) {
    TarjetaSeccion(titulo = "Información de contacto", icono = Icons.Default.Call) {
        OutlinedTextField(
            value = state.telefono,
            onValueChange = { state.telefono = it },
            label = { Text("Teléfono") },
            leadingIcon = { Icon(Icons.Default.Call, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.correo,
            onValueChange = { state.correo = it },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.comuna,
            onValueChange = { state.comuna = it },
            label = { Text("Comuna donde vive") },
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// 5) Nivel de inglés (lista desplegable)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TarjetaIngles(state: FormularioCurriculumState) {
    var abierto by remember { mutableStateOf(false) }

    TarjetaSeccion(titulo = "Nivel de inglés") {
        ExposedDropdownMenuBox(
            expanded = abierto,
            onExpandedChange = { abierto = it }
        ) {
            OutlinedTextField(
                value = state.nivelIngles,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona un nivel") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = abierto) },
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = abierto,
                onDismissRequest = { abierto = false }
            ) {
                nivelesIngles.forEach { nivel ->
                    DropdownMenuItem(
                        text = { Text(nivel) },
                        onClick = {
                            state.nivelIngles = nivel
                            abierto = false
                        }
                    )
                }
            }
        }
    }
}