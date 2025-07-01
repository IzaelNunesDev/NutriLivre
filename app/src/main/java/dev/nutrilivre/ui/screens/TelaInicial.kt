package dev.nutrilivre.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.nutrilivre.navigation.AppScreens
import dev.nutrilivre.model.DadosMockados
import dev.nutrilivre.ui.components.BottomNavigationBar

import androidx.compose.runtime.collectAsState
import dev.nutrilivre.data.AppDatabase
import dev.nutrilivre.data.FavoritesRepository
import dev.nutrilivre.ui.components.ReceitaCard
import dev.nutrilivre.ui.screens.FavoritesViewModel
import dev.nutrilivre.ui.screens.FavoritesViewModelFactory

import androidx.compose.runtime.LaunchedEffect
import dev.nutrilivre.ui.components.ShimmerRecipeCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TelaInicial(navController: NavHostController) {
    val receitas = remember { mutableStateListOf(*DadosMockados.listaDeReceitas.toTypedArray()) }
    var expandedMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModelFactory(context))
    val favoritos by viewModel.favoritos.collectAsState()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1500) // Simulate network delay
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NutriLivre") },
                actions = {
                    IconButton(onClick = { expandedMenu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = {
                                navController.navigate(AppScreens.FavoritosScreen)
                                expandedMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Configurações") },
                            onClick = {
                                navController.navigate(AppScreens.ConfiguracoesScreen)
                                expandedMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Ajuda") },
                            onClick = {
                                navController.navigate(AppScreens.AjudaScreen)
                                expandedMenu = false
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(onClick = {
                // TODO: Navegar para a tela de criação de receita
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Receita")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLoading) {
                items(5) { // Show 5 shimmer cards as placeholders
                    ShimmerRecipeCard()
                }
            } else {
                items(items = receitas, key = { it.id }) { receita ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300)) + slideInVertically(
                            animationSpec = tween(300)
                        ) { it / 2 }
                    ) {
                        ReceitaCard(
                            receita = receita,
                            onReceitaClick = {
                                navController.navigate(
                                    AppScreens.createDetalheRoute(receita.id)
                                )
                            },
                            onFavoritoClick = { viewModel.toggleFavorito(it) },
                            isFavorito = favoritos.any { it.id == receita.id }
                        )
                    }
                }
            }
        }
    }
}
