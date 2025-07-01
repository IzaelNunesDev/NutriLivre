// app/src/main/java/com/example/myapplication/ui/components/BottomNavigationBar.kt
package dev.nutrilivre.ui.components

import dev.nutrilivre.navigation.AppScreens
import dev.nutrilivre.R

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.house_solid), contentDescription = "Receitas", modifier = Modifier.size(24.dp)) },
            label = { Text("Receitas") },
            selected = currentRoute == AppScreens.TelaInicialScreen,
            onClick = { 
                if (currentRoute != AppScreens.TelaInicialScreen) {
                    navController.navigate(AppScreens.TelaInicialScreen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.magnifying_glass_solid), contentDescription = "Buscar", modifier = Modifier.size(24.dp)) },
            label = { Text("Buscar") },
            selected = currentRoute == AppScreens.BuscaScreen,
            onClick = { 
                if (currentRoute != AppScreens.BuscaScreen) {
                    navController.navigate(AppScreens.BuscaScreen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.heart_solid), contentDescription = "Favoritos", modifier = Modifier.size(24.dp)) },
            label = { Text("Favoritos") },
            selected = currentRoute == AppScreens.FavoritosScreen,
            onClick = { 
                if (currentRoute != AppScreens.FavoritosScreen) {
                    navController.navigate(AppScreens.FavoritosScreen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.user_solid), contentDescription = "Configurações", modifier = Modifier.size(24.dp)) },
            label = { Text("Configurações") },
            selected = currentRoute == AppScreens.ConfiguracoesScreen,
            onClick = { 
                if (currentRoute != AppScreens.ConfiguracoesScreen) {
                    navController.navigate(AppScreens.ConfiguracoesScreen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}
