package com.example.wim_100.pantallas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.wim_100.datos.SesionUsuario
import com.example.wim_100.validacion.validarContrasena
import com.example.wim_100.validacion.validarCorreo

@Composable
fun PantallaLogin(onIngresar: () -> Unit) {
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var intento by rememberSaveable { mutableStateOf(false) }
    var credencialesIncorrectas by rememberSaveable { mutableStateOf(false) }

    val errorCorreo = validarCorreo(correo)
    val errorContrasena = validarContrasena(contrasena)

    fun ingresar() {
        intento = true
        if (errorCorreo == null && errorContrasena == null) {
            if (SesionUsuario.iniciarSesion(correo, contrasena)) {
                onIngresar()
            } else {
                credencialesIncorrectas = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Proyecto WIM100",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Ingrese su usuario",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color(0xFFDBDAE5)),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        credencialesIncorrectas = false
                    },
                    label = { Text("Correo") },
                    singleLine = true,
                    isError = intento && errorCorreo != null,
                    supportingText = { if (intento && errorCorreo != null) Text(errorCorreo) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = {
                        contrasena = it
                        credencialesIncorrectas = false
                    },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = intento && errorContrasena != null,
                    supportingText = { if (intento && errorContrasena != null) Text(errorContrasena) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { ingresar() }),
                    modifier = Modifier.fillMaxWidth()
                )

                if (credencialesIncorrectas) {
                    Text(
                        text = "Correo o contraseña incorrectos",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(
                    onClick = { ingresar() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ingresar")
                }
            }
        }
    }
}