package com.example.wim_100.pantallas

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wim_100.PantallaTemporal
import com.example.wim_100.Rutas

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(350))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(350))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(350))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(350))
        }
    ) {

        composable(Rutas.LOGIN) {
            PantallaTemporal("Login") {
                Button(onClick = {
                    navController.navigate(Rutas.MENU) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }) { Text("Ingresar") }
            }
        }

        composable(Rutas.MENU) {
            PantallaMenu(
                onAgregar = { navController.navigate(Rutas.AGREGAR) },
                onEmpezar = { navController.navigate(Rutas.CURRICULUM) }
            )
        }

        composable(Rutas.CURRICULUM) {
            PantallaEvaluacion(
                onTerminar = { navController.navigate(Rutas.RESULTADO) },
                onVolver = { navController.popBackStack() }
            )
        }

        composable(Rutas.AGREGAR) {
            PantallaAgregar(
                onCancelar = { navController.popBackStack() },
                onGuardar = { navController.popBackStack() }
            )
        }

        composable(Rutas.RESULTADO) {
            PantallaTemporal("Resultado final") {
                Button(onClick = { navController.popBackStack(Rutas.MENU, inclusive = false) }) {
                    Text("Confirmar")
                }
            }
        }
    }
}