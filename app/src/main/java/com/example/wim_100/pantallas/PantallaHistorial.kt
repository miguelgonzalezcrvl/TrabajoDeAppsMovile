package com.example.wim_100.pantallas

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
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
import com.example.wim_100.componentes.TarjetaHistorial
import com.example.wim_100.datos.HistorialEnMemoria
import com.example.wim_100.modelo.AccionHistorial
import com.example.wim_100.modelo.RegistroHistorial

@Composable
fun PantallaHistorial(registros: List<RegistroHistorial> = HistorialEnMemoria.registros) {
    // null = mostrar todos
    var filtro by remember { mutableStateOf<AccionHistorial?>(null) }
    val visibles = if (filtro == null) registros else registros.filter { it.accion == filtro }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            FilterChip(
                selected = filtro == null,
                onClick = { filtro = null },
                label = { Text("Todos") }
            )
            AccionHistorial.entries.forEach { accion ->
                FilterChip(
                    selected = filtro == accion,
                    onClick = { filtro = accion },
                    label = { Text(accion.etiqueta) }
                )
            }
        }

        if (visibles.isEmpty()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay acciones en el historial",
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