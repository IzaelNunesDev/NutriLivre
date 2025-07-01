package nutrilivre.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import nutrilivre.navigation.AppScreens
import nutrilivre.ui.components.BottomNavigationBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import nutrilivre.data.AppDatabase
import nutrilivre.data.FavoritesRepository
import nutrilivre.model.Receita

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(navController: NavHostController) {
    val context = LocalContext.current
    val db = androidx.room.Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()
    val repository = FavoritesRepository(db.favoriteDao())
    val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModelFactory(repository))
    val favoritos by viewModel.favoritos.collectAsState()
    val comparacaoViewModel: ComparacaoViewModel = viewModel()
    val selecionadas by comparacaoViewModel.selecionadas.collectAsState()
    var modoSelecao by remember { mutableStateOf(false) }

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
                }) {
                    Text("Comparar")
                }
            }
        }
    ) { paddingValues ->
        if (favoritos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Nenhuma receita favorita adicionada ainda.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp)
            ) {
                items(favoritos) { receita ->
                    val isSelecionada = selecionadas.contains(receita)
                    Card(modifier = Modifier.fillMaxWidth().clickable {
                        if (modoSelecao) {
                            if (isSelecionada) {
                                comparacaoViewModel.removerReceita(receita)
                            } else {
                                comparacaoViewModel.adicionarReceita(receita)
                            }
                        } else {
                            navController.navigate(AppScreens.DetalheScreen.createRoute(receita.id))
                        }
                    }, elevation = CardDefaults.cardElevation(4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
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
                            AsyncImage(
                                model = receita.imagemUrl,
                                contentDescription = receita.nome,
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = receita.nome, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = receita.descricaoCurta, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}