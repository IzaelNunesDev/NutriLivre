package nutrilivre.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import nutrilivre.model.DadosMockados
import nutrilivre.model.Receita
import nutrilivre.navigation.AppScreens
import nutrilivre.ui.components.BottomNavigationBar

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
                                    navController.navigate(AppScreens.DetalheScreen.createRoute(receita.id))
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
