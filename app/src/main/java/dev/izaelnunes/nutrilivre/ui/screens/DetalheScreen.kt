package dev.izaelnunes.nutrilivre.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.izaelnunes.nutrilivre.data.AppDatabase
import dev.izaelnunes.nutrilivre.data.FavoritesRepository
import dev.izaelnunes.nutrilivre.model.DadosMockados
import dev.izaelnunes.nutrilivre.ui.components.BottomNavigationBar
import nutrilivre.ui.screens.FavoritesViewModel
import nutrilivre.ui.screens.FavoritesViewModelFactory
import nutrilivre.ui.theme.AccentOrange
import nutrilivre.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheScreen(navController: NavHostController, receitaId: Int?) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
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
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = r.imagemUrl,
                        contentDescription = r.nome,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { viewModel.toggleFavorito(r) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(50))
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favoritar",
                            tint = if (isFavorite) AccentOrange else Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = r.nome, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = r.descricaoCurta, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Ingredientes:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    r.ingredientes.forEach { ingrediente ->
                        Box(
                            modifier = Modifier
                                .background(PrimaryGreen.copy(alpha = 0.15f), RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(ingrediente, color = PrimaryGreen, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Modo de Preparo:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                r.modoPreparo.forEachIndexed { index, passo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(PrimaryGreen, RoundedCornerShape(50)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${index + 1}", color = Color.White, style = MaterialTheme.typography.labelLarge)
                            }
                            Spacer(Modifier.width(12.dp))
                            Text(passo, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Receitas Relacionadas:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow {
                    items(DadosMockados.listaDeReceitas.take(3)) { relatedReceita ->
                        Card(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(end = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = relatedReceita.imagemUrl,
                                    contentDescription = relatedReceita.nome,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
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

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val mainAxisSpacingPx = mainAxisSpacing.toPx().toInt()
        val crossAxisSpacingPx = crossAxisSpacing.toPx().toInt()

        val lines = mutableListOf<List<Placeable>>()
        var currentLine = mutableListOf<Placeable>()
        var currentLineWidth = 0

        placeables.forEach { placeable ->
            if (currentLineWidth + placeable.width > constraints.maxWidth && currentLine.isNotEmpty()) {
                lines.add(currentLine)
                currentLine = mutableListOf()
                currentLineWidth = 0
            }
            currentLine.add(placeable)
            currentLineWidth += placeable.width + mainAxisSpacingPx
        }
        if (currentLine.isNotEmpty()) {
            lines.add(currentLine)
        }

        val width = constraints.maxWidth
        val height = lines.sumOf { line -> line.maxOfOrNull { it.height } ?: 0 } +
                (lines.size - 1).coerceAtLeast(0) * crossAxisSpacingPx

        layout(width, height) {
            var y = 0
            lines.forEach { line ->
                var x = 0
                val lineHeight = line.maxOfOrNull { it.height } ?: 0
                line.forEach { placeable ->
                    placeable.placeRelative(x, y)
                    x += placeable.width + mainAxisSpacingPx
                }
                y += lineHeight + crossAxisSpacingPx
            }
        }
    }
}
