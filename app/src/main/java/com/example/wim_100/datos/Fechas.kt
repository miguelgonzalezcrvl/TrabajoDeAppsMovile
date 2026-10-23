package com.example.wim_100.datos

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun fechaDeHoy(): String =
    SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("es-CL")).format(Date())