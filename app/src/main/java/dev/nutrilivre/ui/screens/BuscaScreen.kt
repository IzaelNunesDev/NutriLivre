package dev.nutrilivre.ui.screens

import dev.nutrilivre.navigation.AppScreens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.nutrilivre.model.DadosMockados
import dev.nutrilivre.ui.components.BottomNavigationBar
import dev.nutrilivre.ui.screens.ComparacaoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscaScreen(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    val receitas = remember { DadosMockados.listaDeReceitas }
    val filteredReceitas = remember(searchText) {
        if (searchText.isBlank()) {
            receitas
        } else {
            receitas.filter {
                it.nome.contains(searchText, ignoreCase = true) ||
                        it.ingredientes.any { ingrediente -> ingrediente.contains(searchText, ignoreCase = true) }
            }
        }
    }
    val comparacaoViewModel: ComparacaoViewModel = viewModel()
    val selecionadas by comparacaoViewModel.selecionadas.collectAsState()
    var modoSelecao by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Receitas") },
                actions = {
                    IconButton(onClick = { modoSelecao = !modoSelecao }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = {
            if (modoSelecao && selecionadas.size >= 2) {
                FloatingActionButton(onClick = {
                    navController.navigate("comparacao")
                }) {
                    Text("Comparar")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Buscar Receitas") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(filteredReceitas) { receita ->
                    val isSelecionada = selecionadas.contains(receita)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
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
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            if (modoSelecao) {
                                Checkbox(
                                    checked = isSelecionada,
                                    onCheckedChange = {
                                        if (it) comparacaoViewModel.adicionarReceita(receita)
                                        else comparacaoViewModel.removerReceita(receita)
                                    }
                                )
                                Spacer(Modifier.width(8.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                AsyncImage(
                                    model = receita.imagemUrl,
                                    contentDescription = receita.nome,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = receita.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = receita.descricaoCurta,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
