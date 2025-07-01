package nutrilivre.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import nutrilivre.model.Receita

@Composable
fun ComparacaoScreen(viewModel: ComparacaoViewModel = viewModel()) {
    val selecionadas by viewModel.selecionadas.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Comparação de Receitas", style = MaterialTheme.typography.headlineSmall)
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

@Composable
fun ReceitaComparacaoCard(receita: Receita) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .background(Color(0xFFF5F5F5))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(receita.nome, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Text("${receita.calorias} kcal", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ComparacaoTabela(receitas: List<Receita>) {
    val labels = listOf("Proteínas (g)", "Carboidratos (g)", "Gorduras (g)", "Fibras (g)")
    val valores: List<(Receita) -> String> = listOf(
        { it.proteinas.toString() },
        { it.carboidratos.toString() },
        { it.gorduras.toString() },
        { it.fibras.toString() }
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        labels.forEachIndexed { i, label ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(label, modifier = Modifier.weight(1f))
                receitas.forEach { receita ->
                    Text(valores[i](receita), modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
} 