package com.example.wim_100.pantallas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wim_100.componentes.DatosPostulante
import com.example.wim_100.componentes.TarjetaEvaluacionCriterio
import com.example.wim_100.datos.CriteriosEnMemoria
import com.example.wim_100.datos.PostulantesEnMemoria
import com.example.wim_100.modelo.Cumplimiento
import com.example.wim_100.modelo.ResultadoEvaluacion
import com.example.wim_100.modelo.calcularResultado

private data class ItemSeccion(val titulo: String, val icono: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEvaluacion(
    postulanteId: Int,
    onCancelar: () -> Unit,
    onTerminar: (ResultadoEvaluacion) -> Unit
) {
    val postulante = PostulantesEnMemoria.buscar(postulanteId) ?: return

    // Los criterios se congelan al empezar, para que no cambien a mitad de la evaluación
    val criterios = remember { CriteriosEnMemoria.criterios.toList() }
    val respuestas = remember {
        mutableStateListOf<Cumplimiento?>().apply { repeat(criterios.size) { add(null) } }
    }
    var pestana by remember { mutableIntStateOf(0) }

    val respondidos = respuestas.count { it != null }
    val completo = criterios.isNotEmpty() && respondidos == criterios.size

    val secciones = listOf(
        ItemSeccion("Currículum", Icons.Default.AccountBox),
        ItemSeccion("Criterios", Icons.Default.CheckCircle)
    )

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Evaluar Currículum") },
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
            NavigationBar {
                secciones.forEachIndexed { indice, item ->
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
                visible = pestana == 1,
                enter = slideInHorizontally(initialOffsetX = { it * 2 }),
                exit = slideOutHorizontally(targetOffsetX = { it * 2 })
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (completo) {
                            onTerminar(calcularResultado(criterios, respuestas.filterNotNull()))
                        }
                    },
                    containerColor = if (completo) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (completo) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                    icon = { Icon(Icons.Default.Check, contentDescription = null) },
                    text = { Text("Terminar") }
                )
            }
        }
    ) { innerPadding ->
        when (pestana) {
            0 -> DatosPostulante(
                postulante = postulante,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            )

            else -> if (criterios.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aún no hay criterios definidos. Un administrador debe agregarlos desde Perfil.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Evaluados: $respondidos de ${criterios.size}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    criterios.forEachIndexed { indice, criterio ->
                        TarjetaEvaluacionCriterio(
                            criterio = criterio,
                            seleccion = respuestas[indice],
                            onSeleccionar = { respuestas[indice] = it }
                        )
                    }
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
        }
    }
}