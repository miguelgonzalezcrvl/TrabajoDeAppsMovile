package com.example.wim_100.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wim_100.componentes.RegistroHistorial
import com.example.wim_100.componentes.ResultadoEvaluacion
import com.example.wim_100.componentes.TarjetaHistorial

private val historialDePrueba = listOf(
    RegistroHistorial("Camila Rojas Fuentes", "24 años · evaluada el 15-10-2026", ResultadoEvaluacion.APROBADO),
    RegistroHistorial("Nombre Completo", "Edad · fecha de evaluación", ResultadoEvaluacion.NO_APROBADO),
    RegistroHistorial("Otra Postulante", "Edad · fecha de evaluación", ResultadoEvaluacion.APROBADO),
    RegistroHistorial("Una Más", "Edad · fecha de evaluación", ResultadoEvaluacion.APROBADO),
    RegistroHistorial("Última de Prueba", "Edad · fecha de evaluación", ResultadoEvaluacion.NO_APROBADO)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaHistorial(registros: List<RegistroHistorial> = historialDePrueba) {
    // null = mostrar todos
    var filtro by remember { mutableStateOf<ResultadoEvaluacion?>(null) }
    val visibles = if (filtro == null) registros else registros.filter { it.resultado == filtro }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = filtro == null,
                onClick = { filtro = null },
                label = { Text("Todos") }
            )
            ResultadoEvaluacion.entries.forEach { resultado ->
                FilterChip(
                    selected = filtro == resultado,
                    onClick = { filtro = resultado },
                    label = { Text(resultado.etiqueta) }
                )
            }
        }

        if (visibles.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay evaluaciones en el historial",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(visibles) { registro ->
                    TarjetaHistorial(registro = registro)
                }
            }
        }
    }
}