package com.example.wim_100.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class CriterioState(definicion: String = "", puntos: Int = 1) {
    var definicion by mutableStateOf(definicion)
    var puntos by mutableIntStateOf(puntos)
}

private fun etiquetaPuntos(puntos: Int): String = when (puntos) {
    1 -> "Muy prescindible"
    2 -> "Poco importante"
    3 -> "Algo importante"
    4 -> "Importante"
    5 -> "Muy importante"
    6 -> "Imprescindible"
    else -> "Casi obligatorio"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaCriterio(
    numero: Int,
    criterio: CriterioState,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var abierto by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Criterio $numero",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar criterio")
                }
            }

            OutlinedTextField(
                value = criterio.definicion,
                onValueChange = { criterio.definicion = it },
                label = { Text("Definición del criterio") },
                placeholder = { Text("Ej: Nivel de inglés alto") },
                minLines = 2,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = abierto,
                onExpandedChange = { abierto = it }
            ) {
                OutlinedTextField(
                    value = "${criterio.puntos} – ${etiquetaPuntos(criterio.puntos)}",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Puntos que vale") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = abierto) },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = abierto,
                    onDismissRequest = { abierto = false }
                ) {
                    (1..7).forEach { puntos ->
                        DropdownMenuItem(
                            text = { Text("$puntos – ${etiquetaPuntos(puntos)}") },
                            onClick = {
                                criterio.puntos = puntos
                                abierto = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaAgregarCriterio(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar criterio",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}