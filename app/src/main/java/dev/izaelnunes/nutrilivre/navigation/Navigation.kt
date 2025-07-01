package dev.izaelnunes.nutrilivre.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.izaelnunes.nutrilivre.ui.screens.BuscaScreen
import dev.izaelnunes.nutrilivre.ui.screens.DetalheScreen
import dev.izaelnunes.nutrilivre.ui.screens.FavoritosScreen
import dev.izaelnunes.nutrilivre.ui.screens.TelaInicial
import nutrilivre.ui.screens.AjudaScreen
import nutrilivre.ui.screens.ConfiguracoesScreen


// Rotas nomeadas
sealed class AppScreens(val route: String) {
    object TelaInicialScreen : AppScreens("tela_inicial")
    object FavoritosScreen : AppScreens("favoritos")
    object ConfiguracoesScreen : AppScreens("configuracoes")
    object AjudaScreen : AppScreens("ajuda")
    object BuscaScreen : AppScreens("busca")

    object DetalheScreen : AppScreens("detalhe_receita/{receitaId}") {
        fun createRoute(receitaId: Int): String {
            return "detalhe_receita/$receitaId"
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.TelaInicialScreen.route
    ) {
        composable(AppScreens.TelaInicialScreen.route) {
            TelaInicial(navController)
        }
        composable(AppScreens.FavoritosScreen.route) {
            FavoritosScreen(navController)
        }
        composable(AppScreens.ConfiguracoesScreen.route) {
            ConfiguracoesScreen(onBack = { navController.popBackStack() })
        }
        composable(AppScreens.AjudaScreen.route) {
            AjudaScreen(navController)
        }
        composable(AppScreens.BuscaScreen.route) {
            BuscaScreen(navController)
        }
        composable(AppScreens.DetalheScreen.route) { backStackEntry ->
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
