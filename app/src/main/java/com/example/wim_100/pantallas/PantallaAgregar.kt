package com.example.wim_100.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.wim_100.componentes.FormularioCurriculum
import com.example.wim_100.componentes.FormularioCurriculumState

private data class ItemPestana(val titulo: String, val icono: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregar(onCancelar: () -> Unit, onGuardar: () -> Unit) {
    var pestana by remember { mutableIntStateOf(0) }
    val formulario = remember { FormularioCurriculumState() }

    val items = listOf(
        ItemPestana("Currículum", Icons.Default.Edit),
        ItemPestana("Evaluación", Icons.Default.CheckCircle)
    )

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
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NavigationBarDefaults.containerColor)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationBar(
                    modifier = Modifier.weight(1f),
                    windowInsets = WindowInsets(0, 0, 0, 0)
                ) {
                    items.forEachIndexed { indice, item ->
                        NavigationBarItem(
                            selected = pestana == indice,
                            onClick = { pestana = indice },
                            icon = { Icon(item.icono, contentDescription = item.titulo) },
                            label = { Text(item.titulo) }
                        )
                    }
                }
                Button(
                    onClick = onGuardar,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text("Guardar")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (pestana) {
                0 -> FormularioCurriculum(
                    state = formulario,
                    modifier = Modifier.fillMaxSize()
                )
                1 -> Text("Evaluación")
            }
        }
    }
}