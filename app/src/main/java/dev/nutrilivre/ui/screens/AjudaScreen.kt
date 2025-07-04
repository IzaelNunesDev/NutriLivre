package dev.nutrilivre.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.painterResource
import dev.nutrilivre.R
import dev.nutrilivre.model.DadosMockados
import dev.nutrilivre.ui.components.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjudaScreen(navController: NavHostController) {
    val perguntas = DadosMockados.listaDePerguntasFrequentes

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajuda e Suporte") },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(painter = painterResource(id = R.drawable.brain_solid), contentDescription = "Ajuda", modifier = Modifier.size(24.dp))
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Perguntas Frequentes", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(perguntas) { pergunta ->
                    Text("- $pergunta", style = MaterialTheme.typography.bodyLarge)
                    Divider()
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {  }) {
                Text("Fale com o Suporte (Simulado)")
            }
        }
    }
}

@Composable
fun Divider() {
    androidx.compose.material3.Divider()
}
