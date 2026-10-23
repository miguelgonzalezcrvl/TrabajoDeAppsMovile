package com.example.wim_100.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wim_100.modelo.Postulante

@Composable
fun DatosPostulante(postulante: Postulante, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // 1) Foto + nombre + edad
        TarjetaSeccion {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarPersona(tamano = 90.dp, fotoUri = postulante.fotoUri)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = postulante.nombre.ifBlank { "Sin nombre" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    if (postulante.edad > 0) {
                        Text(
                            text = "${postulante.edad} años",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // 2) Descripción
        TarjetaSeccion(titulo = "Descripción", icono = Icons.Default.Info) {
            Text(postulante.descripcion.ifBlank { "Sin descripción" })
        }

        // 3) Estudios
        TarjetaSeccion(titulo = "Estudios", icono = Icons.Default.Star) {
            if (postulante.estudios.isEmpty()) {
                Text("Sin estudios registrados")
            }
            postulante.estudios.forEach { estudio ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFDBDAE5)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F5FA))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = estudio.titulo.ifBlank { "—" },
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = estudio.institucion.ifBlank { "—" },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (estudio.anioTermino.isNotBlank()) {
                            Text(
                                text = "Año de término: ${estudio.anioTermino}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // 4) Contacto
        TarjetaSeccion(titulo = "Información de contacto", icono = Icons.Default.Call) {
            FilaDato(Icons.Default.Call, postulante.telefono)
            FilaDato(Icons.Default.Email, postulante.correo)
            FilaDato(Icons.Default.LocationOn, postulante.comuna)
        }

        // 5) Nivel de inglés
        TarjetaSeccion(titulo = "Nivel de inglés") {
            Text(postulante.nivelIngles.ifBlank { "Sin indicar" })
        }
    }
}

@Composable
private fun FilaDato(icono: ImageVector, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = valor.ifBlank { "—" }, style = MaterialTheme.typography.bodyLarge)
    }
}