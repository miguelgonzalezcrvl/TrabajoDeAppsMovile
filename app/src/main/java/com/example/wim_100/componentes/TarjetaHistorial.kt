package com.example.wim_100.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wim_100.modelo.AccionHistorial
import com.example.wim_100.modelo.RegistroHistorial

// Devuelve (color de fondo, color del texto) de la etiqueta de cada acción
private fun coloresDe(accion: AccionHistorial): Pair<Color, Color> = when (accion) {
    AccionHistorial.AGREGADO -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
    AccionHistorial.APROBADO -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
    AccionHistorial.NO_APROBADO -> Color(0xFFFFEBEE) to Color(0xFFC62828)
}

@Composable
fun TarjetaHistorial(
    registro: RegistroHistorial,
    modifier: Modifier = Modifier
) {
    val (fondo, texto) = coloresDe(registro.accion)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(AlturaTarjeta),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarPersona(tamano = TamanoAvatarTarjeta, fotoUri = registro.postulante.fotoUri)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = registro.postulante.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "por ${registro.usuario} · ${registro.fecha}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(shape = RoundedCornerShape(50), color = fondo) {
                    Text(
                        text = registro.accion.etiqueta,
                        color = texto,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}