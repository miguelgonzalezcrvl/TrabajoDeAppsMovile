package com.example.wim_100.componentes

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage

/** Círculo con la foto de la persona (o un ícono naranja si no hay foto). */
@Composable
fun AvatarPersona(
    tamano: Dp,
    modifier: Modifier = Modifier,
    fotoUri: Uri? = null,
    onClick: (() -> Unit)? = null
) {
    Box(modifier = modifier.size(tamano)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color(0xFFFFB74D))
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            if (fotoUri != null) {
                AsyncImage(
                    model = fotoUri,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(tamano * 0.66f)
                )
            }
        }

        // Lápiz que indica que se puede editar
        if (onClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(tamano * 0.3f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(tamano * 0.18f)
                )
            }
        }
    }
}