package dev.nutrilivre.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import dev.nutrilivre.R
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavHostController
import dev.nutrilivre.model.Receita
import dev.nutrilivre.ui.theme.PrimaryGreen
import dev.nutrilivre.ui.theme.SecondaryBlue
import dev.nutrilivre.ui.theme.AccentOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparacaoScreen(navController: NavHostController, viewModel: ComparacaoViewModel = viewModel()) {
    val selecionadas by viewModel.selecionadas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comparação de Receitas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Icon(painter = painterResource(id = R.drawable.chart_pie_solid), contentDescription = "Comparação", modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(16.dp))
            if (selecionadas.isEmpty()) {
                Text("Selecione receitas para comparar.")
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    selecionadas.forEach { receita ->
                        ReceitaComparacaoCard(receita)
                    }
                }
                Spacer(Modifier.height(24.dp))
                // Tabela comparativa
                ComparacaoTabela(selecionadas)
            }
        }
    }
}

@Composable
fun ReceitaComparacaoCard(receita: Receita) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF5F5F5))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(receita.nome, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text("${receita.calorias} kcal", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ComparacaoTabela(receitas: List<Receita>) {
    val labels = listOf("Proteínas (g)", "Carboidratos (g)", "Gorduras (g)", "Fibras (g)")
    val valores: List<(Receita) -> Float> = listOf(
        { it.proteinas },
        { it.carboidratos },
        { it.gorduras },
        { it.fibras }
    )
    val cores = listOf(PrimaryGreen, SecondaryBlue, AccentOrange, MaterialTheme.colorScheme.secondary)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        labels.forEachIndexed { i, label ->
            val maxValor = receitas.maxOfOrNull { valores[i](it) } ?: 1f
            Text(label, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            receitas.forEachIndexed { idx, receita ->
                val valor = valores[i](receita)
                val proporcao = if (maxValor > 0) valor / maxValor else 0f
                val animatedProporcao by animateFloatAsState(targetValue = proporcao, label = "")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(cores.getOrElse(idx) { MaterialTheme.colorScheme.primary }.copy(alpha = 0.7f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(animatedProporcao)
                                .height(20.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(cores.getOrElse(idx) { MaterialTheme.colorScheme.primary })
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(String.format("%.1f", valor), style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
} 