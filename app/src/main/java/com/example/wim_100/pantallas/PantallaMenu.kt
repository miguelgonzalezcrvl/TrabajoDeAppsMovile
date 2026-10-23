package com.example.wim_100.pantallas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.wim_100.componentes.TarjetaCurriculum
import androidx.compose.material3.MaterialTheme
import com.example.wim_100.datos.PostulantesEnMemoria
import com.example.wim_100.modelo.EstadoPostulante

private data class ItemNavegacion(val titulo: String, val icono: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(
    onAgregar: () -> Unit,
    onEvaluar: (Int) -> Unit,
    onAdministrarCriterios: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    var pestana by remember { mutableIntStateOf(0) }

    val items = listOf(
        ItemNavegacion("Currículums", Icons.Default.AccountBox),
        ItemNavegacion("Historial", Icons.Default.DateRange),
        ItemNavegacion("Perfil", Icons.Default.Person)
    )

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = { Text("Proyecto WIM100") })
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(red = 223, green = 222, blue = 229)
                )
            }
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { indice, item ->
                    NavigationBarItem(
                        selected = pestana == indice,
                        onClick = { pestana = indice },
                        icon = { Icon(item.icono, contentDescription = item.titulo) },
                        label = { Text(item.titulo) }
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = pestana == 0,
                enter = slideInHorizontally(initialOffsetX = { it * 2 }),
                exit = slideOutHorizontally(targetOffsetX = { it * 2 })
            ) {
                FloatingActionButton(onClick = onAgregar) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar currículum")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (pestana) {
                0 -> {
                    val pendientes = PostulantesEnMemoria.postulantes
                        .filter { it.estado == EstadoPostulante.PENDIENTE }

                    if (pendientes.isEmpty()) {
                        Text(
                            text = "No hay currículums pendientes",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(pendientes, key = { it.id }) { postulante ->
                                TarjetaCurriculum(
                                    postulante = postulante,
                                    onEvaluar = { onEvaluar(postulante.id) }
                                )
                            }
                        }
                    }
                }
                1 ->  PantallaHistorial()
                2 -> PantallaPerfil(
                    onAdministrarCriterios = onAdministrarCriterios,
                    onCerrarSesion = onCerrarSesion
                )
            }
        }
    }
}