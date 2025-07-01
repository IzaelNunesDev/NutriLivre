package nutrilivre.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import nutrilivre.ui.components.BottomNavigationBar
import androidx.compose.ui.platform.LocalContext
import nutrilivre.data.AppDatabase
import nutrilivre.data.FavoritesRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheScreen(navController: NavHostController, receitaId: Int?) {
    val context = LocalContext.current
    val db = androidx.room.Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()
    val repository = FavoritesRepository(db.favoriteDao())
    val viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModelFactory(repository))
    val favoritos by viewModel.favoritos.collectAsState()
    val receita = remember { DadosMockados.listaDeReceitas.find { it.id == receitaId } }
    val isFavorite = favoritos.any { it.id == receita?.id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(receita?.nome ?: "Detalhe") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        receita?.let { r ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = r.imagemUrl,
                    contentDescription = r.nome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = r.nome, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = r.descricaoCurta, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Ingredientes:", style = MaterialTheme.typography.titleMedium)
                r.ingredientes.forEach { ingrediente ->
                    Text(text = "- $ingrediente")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Modo de Preparo:", style = MaterialTheme.typography.titleMedium)
                r.modoPreparo.forEachIndexed { index, passo ->
                    Text(text = "${index + 1}. $passo")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        viewModel.toggleFavorito(r)
                    }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favoritar",
                            tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isFavorite) "Remover dos Favoritos" else "Adicionar aos Favoritos")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { }, enabled = false) {
                        Text("Ouvir/Ver")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Receitas Relacionadas:", style = MaterialTheme.typography.titleMedium)
                LazyRow {
                    items(DadosMockados.listaDeReceitas.take(3)) { relatedReceita ->
                        Card(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(end = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = relatedReceita.imagemUrl,
                                    contentDescription = relatedReceita.nome,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(relatedReceita.nome, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Receita não encontrada.", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
