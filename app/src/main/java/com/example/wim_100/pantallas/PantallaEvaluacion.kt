package com.example.wim_100.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaEvaluacion(onTerminar: () -> Unit, onVolver: () -> Unit) {
    var pestana by remember { mutableIntStateOf(0) }
    val titulos = listOf("Currículum", "Criterios")

    Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {

        PrimaryTabRow(selectedTabIndex = pestana) {
            titulos.forEachIndexed { indice, titulo ->
                Tab(
                    selected = pestana == indice,
                    onClick = { pestana = indice },
                    text = { Text(titulo) }
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pestana == 0) {
                Text("Contenido del currículum")
            } else {
                Text("Criterios de contratación")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = onVolver) { Text("Volver") }
            Button(onClick = onTerminar) { Text("Terminar") }
        }
    }
}