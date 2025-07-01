package dev.nutrilivre.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.nutrilivre.data.AppDatabase
import dev.nutrilivre.data.FavoritesRepository
import dev.nutrilivre.navigation.AppScreens
import dev.nutrilivre.ui.components.BottomNavigationBar
import dev.nutrilivre.ui.components.ReceitaCard
import dev.nutrilivre.ui.screens.ComparacaoViewModel
import dev.nutrilivre.ui.screens.FavoritesViewModel
import dev.nutrilivre.ui.screens.FavoritesViewModelFactory
import dev.nutrilivre.ui.theme.PrimaryGreen

import androidx.compose.runtime.LaunchedEffect
import dev.nutrilivre.ui.components.ShimmerRecipeCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModelFactory(context))
    val favoritos by viewModel.favoritos.collectAsState()
    val comparacaoViewModel: ComparacaoViewModel = viewModel()
    val selecionadas by comparacaoViewModel.selecionadas.collectAsState()
    var modoSelecao by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1500) // Simulate network delay
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favoritos") }, actions = {
                IconButton(onClick = { modoSelecao = !modoSelecao }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                }
            })
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = {
            if (modoSelecao && selecionadas.size >= 2) {
                FloatingActionButton(onClick = {
                    navController.navigate("comparacao")
                }, containerColor = PrimaryGreen) {
                    Text("Comparar")
                }
            }
        }
    ) { paddingValues ->
        if (favoritos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("Nenhuma receita favorita ainda!", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Toque no coração das receitas para favoritar.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        } else {
            if (isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) { // Show 5 shimmer cards as placeholders
                        ShimmerRecipeCard()
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoritos) { receita ->
                        val isSelecionada = selecionadas.contains(receita)
                        Box {
                            ReceitaCard(
                                receita = receita,
                                onReceitaClick = {
                                    if (modoSelecao) {
                                        if (isSelecionada) {
                                            comparacaoViewModel.removerReceita(receita)
                                        } else {
                                            comparacaoViewModel.adicionarReceita(receita)
                                        }
                                    } else {
                                        navController.navigate(AppScreens.createDetalheRoute(receita.id))
                                    }
                                },
                                onFavoritoClick = { viewModel.toggleFavorito(it) },
                                isFavorito = true
                            )
                            if (modoSelecao) {
                                Checkbox(
                                    checked = isSelecionada,
                                    onCheckedChange = {
                                        if (it) comparacaoViewModel.adicionarReceita(receita)
                                        else comparacaoViewModel.removerReceita(receita)
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}