package com.example.wim_100.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wim_100.componentes.FormularioCurriculum
import com.example.wim_100.componentes.FormularioCurriculumState
import com.example.wim_100.componentes.aPostulante
import com.example.wim_100.modelo.Postulante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregar(onCancelar: () -> Unit, onGuardar: (Postulante) -> Unit) {
    val formulario = remember { FormularioCurriculumState() }
    val puedeGuardar = formulario.nombre.isNotBlank()

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Agregar Currículum") },
                    navigationIcon = {
                        TextButton(onClick = onCancelar) { Text("Cancelar") }
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
                onClick = { if (puedeGuardar) onGuardar(formulario.aPostulante()) },
                containerColor = if (puedeGuardar) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (puedeGuardar) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
                icon = { Icon(Icons.Default.Check, contentDescription = null) },
                text = { Text("Guardar") }
            )
        }
    ) { innerPadding ->
        FormularioCurriculum(
            state = formulario,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}