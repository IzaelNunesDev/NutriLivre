package dev.nutrilivre.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dev.nutrilivre.ui.screens.BuscaScreen
import dev.nutrilivre.ui.screens.DetalheScreen
import dev.nutrilivre.ui.screens.FavoritosScreen
import dev.nutrilivre.ui.screens.TelaInicial
import dev.nutrilivre.ui.screens.ConfiguracoesScreen
import dev.nutrilivre.ui.screens.AjudaScreen
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = AppScreens.TelaInicialScreen
    ) {
        composable(AppScreens.TelaInicialScreen,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) {
            TelaInicial(navController)
        }
        composable(AppScreens.FavoritosScreen,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) {
            FavoritosScreen(navController)
        }
        composable(AppScreens.ConfiguracoesScreen,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) {
            ConfiguracoesScreen(onBack = { navController.popBackStack() })
        }
        composable(AppScreens.AjudaScreen,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) {
            AjudaScreen(navController)
        }
        composable(AppScreens.BuscaScreen,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) {
            BuscaScreen(navController)
        }
        composable(AppScreens.DetalheScreen,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300)) }
        ) { backStackEntry ->
            val receitaId = backStackEntry.arguments?.getString("receitaId")?.toIntOrNull()
            if (receitaId != null) {
                DetalheScreen(navController = navController, receitaId = receitaId)
            } else {
                // Fallback seguro em caso de ID inválido
                Text("Erro: receitaId inválido ou ausente")
            }
        }
    }
}


