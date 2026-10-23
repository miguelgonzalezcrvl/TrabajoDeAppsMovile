package com.example.wim_100.pantallas

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wim_100.Rutas
import com.example.wim_100.datos.EvaluacionEnCurso
import com.example.wim_100.datos.HistorialEnMemoria
import com.example.wim_100.datos.PostulantesEnMemoria
import com.example.wim_100.datos.SesionUsuario
import com.example.wim_100.modelo.AccionHistorial
import com.example.wim_100.modelo.EstadoPostulante

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
            PantallaLogin(
                onIngresar = {
                    navController.navigate(Rutas.MENU) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.MENU) {
            PantallaMenu(
                onAgregar = { navController.navigate(Rutas.AGREGAR) },
                onEvaluar = { id -> navController.navigate(Rutas.evaluar(id)) },
                onAdministrarCriterios = { navController.navigate(Rutas.EDITAR_CRITERIOS) },
                onCerrarSesion = {
                    SesionUsuario.cerrarSesion()
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.MENU) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Rutas.EVALUAR,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: return@composable
            PantallaEvaluacion(
                postulanteId = id,
                onCancelar = { navController.popBackStack() },
                onTerminar = { resultado ->
                    EvaluacionEnCurso.resultado = resultado
                    navController.navigate(Rutas.resultado(id))
                }
            )
        }

        composable(
            route = Rutas.RESULTADO,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: return@composable
            PantallaResultado(
                postulanteId = id,
                onReevaluar = { navController.popBackStack() },
                onTerminarProceso = { aprobado ->
                    val postulante = PostulantesEnMemoria.buscar(id)
                    if (postulante != null) {
                        PostulantesEnMemoria.actualizarEstado(
                            id,
                            if (aprobado) EstadoPostulante.APROBADO else EstadoPostulante.RECHAZADO
                        )
                        HistorialEnMemoria.registrar(
                            postulante,
                            if (aprobado) AccionHistorial.APROBADO else AccionHistorial.NO_APROBADO
                        )
                    }
                    navController.popBackStack(Rutas.MENU, inclusive = false)
                }
            )
        }

        composable(Rutas.AGREGAR) {
            PantallaAgregar(
                onCancelar = { navController.popBackStack() },
                onGuardar = { postulante ->
                    val guardado = PostulantesEnMemoria.agregar(postulante)
                    HistorialEnMemoria.registrar(guardado, AccionHistorial.AGREGADO)
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.EDITAR_CRITERIOS) {
            PantallaCriterios(
                onCancelar = { navController.popBackStack() },
                onGuardar = { navController.popBackStack() }
            )
        }
    }
}