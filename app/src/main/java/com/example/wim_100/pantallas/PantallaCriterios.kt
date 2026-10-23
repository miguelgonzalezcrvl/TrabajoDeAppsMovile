package com.example.wim_100.pantallas

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wim_100.componentes.CriterioState
import com.example.wim_100.componentes.TarjetaAgregarCriterio
import com.example.wim_100.componentes.TarjetaCriterio
import com.example.wim_100.datos.CriteriosEnMemoria
import com.example.wim_100.modelo.Criterio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCriterios(onCancelar: () -> Unit, onGuardar: () -> Unit) {
    // Borrador: se parte con lo que ya estaba guardado
    val borrador = remember {
        mutableStateListOf<CriterioState>().apply {
            CriteriosEnMemoria.criterios.forEach { add(CriterioState(it.definicion, it.puntos)) }
        }
    }

    val total = borrador.sumOf { it.puntos }
    val totalAnimado by animateIntAsState(targetValue = total, label = "totalPuntos")
    val puedeGuardar = borrador.all { it.definicion.isNotBlank() }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Editar Criterios") },
                    navigationIcon = {
                        TextButton(onClick = onCancelar) { Text("Cancelar") }
                    },
                    actions = {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Text(
                                text = "Total: $totalAnimado pts",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(red = 223, green = 222, blue = 229)
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (puedeGuardar) {
                        CriteriosEnMemoria.reemplazar(
                            borrador.map { Criterio(it.definicion.trim(), it.puntos) }
                        )
                        onGuardar()
                    }
                },
                containerColor = if (puedeGuardar) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (puedeGuardar) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
                icon = { Icon(Icons.Default.Check, contentDescription = null) },
                text = { Text("Guardar") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            borrador.forEachIndexed { indice, criterio ->
                key(criterio) {
                    TarjetaCriterio(
                        numero = indice + 1,
                        criterio = criterio,
                        onEliminar = { borrador.remove(criterio) }
                    )
                }
            }

            TarjetaAgregarCriterio(onClick = { borrador.add(CriterioState()) })

            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}