package com.example.wim_100.pantallas

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wim_100.componentes.AvatarPersona
import com.example.wim_100.datos.EvaluacionEnCurso
import com.example.wim_100.datos.PostulantesEnMemoria
import com.example.wim_100.modelo.PORCENTAJE_APROBACION
import com.example.wim_100.modelo.comoPuntaje
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaResultado(
    postulanteId: Int,
    onReevaluar: () -> Unit,
    onTerminarProceso: (Boolean) -> Unit
) {
    val postulante = PostulantesEnMemoria.buscar(postulanteId)
    val resultado = EvaluacionEnCurso.resultado
    if (postulante == null || resultado == null) return

    // Animación: el porcentaje y la barra suben desde 0
    val progreso = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progreso.animateTo(
            targetValue = (resultado.porcentaje / 100.0).toFloat().coerceIn(0f, 1f),
            animationSpec = tween(900)
        )
    }

    val fondoEtiqueta = if (resultado.aprobado) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val textoEtiqueta = if (resultado.aprobado) Color(0xFF2E7D32) else Color(0xFFC62828)

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = { Text("Resultado final") })
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(red = 223, green = 222, blue = 229)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AvatarPersona(tamano = 96.dp, fotoUri = postulante.fotoUri)
                    Text(
                        text = postulante.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = String.format(
                            Locale.forLanguageTag("es-CL"), "%.1f%%", progreso.value * 100f
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    LinearProgressIndicator(
                        progress = { progreso.value },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Puntaje obtenido: ${resultado.puntajeObtenido.comoPuntaje()} de ${resultado.puntajeTotal} puntos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Surface(shape = RoundedCornerShape(50), color = fondoEtiqueta) {
                        Text(
                            text = if (resultado.aprobado) "Aprobado" else "Rechazado",
                            color = textoEtiqueta,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                    Text(
                        text = "Se necesita al menos un ${PORCENTAJE_APROBACION.toInt()}% para aprobar",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onReevaluar,
                    modifier = Modifier.weight(1f)
                ) { Text("Re-evaluar") }
                Button(
                    onClick = { onTerminarProceso(resultado.aprobado) },
                    modifier = Modifier.weight(1f)
                ) { Text("Terminar proceso") }
            }
        }
    }
}