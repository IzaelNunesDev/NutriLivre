package dev.nutrilivre.model

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


data class Receita(
    val id: Int,
    val nome: String,
    val descricaoCurta: String,
    val imagemUrl: String,
    val ingredientes: List<String>,
    val modoPreparo: List<String>,
    val tempoPreparo: String,
    val porcoes: Int,
    val proteinas: Float, // gramas
    val carboidratos: Float, // gramas
    val gorduras: Float, // gramas
    val calorias: Int, // kcal
    val fibras: Float, // gramas
    var isFavorita: Boolean = false
)

object DadosMockados {
    val listaDeReceitas = listOf(
        Receita(
            id = 1,
            nome = "Salada de Quinoa com Vegetais",
            descricaoCurta = "Uma salada nutritiva e refrescante.",
            imagemUrl = "https://cdn.vidaativa.pt/uploads/2020/07/salada-quinoa-colorida.jpg",
            ingredientes = listOf(
                "1 xícara de quinoa cozida",
                "1 pepino picado",
                "1 tomate picado"
            ),
            modoPreparo = listOf("Misture os vegetais.", "Adicione a quinoa."),
            tempoPreparo = "20 min",
            porcoes = 2,
            proteinas = 8.0f,
            carboidratos = 35.0f,
            gorduras = 4.0f,
            calorias = 210,
            fibras = 5.0f
        ),
        Receita(
            id = 2,
            nome = "Frango Assado com Batata Doce",
            descricaoCurta = "Um prato clássico e saudável.",
            imagemUrl = "https://assets.unileversolutions.com/recipes-v2/36770.jpg",
            ingredientes = listOf("2 filés de frango", "2 batatas doces"),
            modoPreparo = listOf("Tempere o frango.", "Asse com as batatas."),
            tempoPreparo = "50 min",
            porcoes = 2,
            proteinas = 32.0f,
            carboidratos = 40.0f,
            gorduras = 7.0f,
            calorias = 350,
            fibras = 6.0f
        ),
        Receita(
            id = 3,
            nome = "Smoothie de Frutas Vermelhas",
            descricaoCurta = "Delicioso e cheio de antioxidantes.",
            imagemUrl = "https://thvnext.bing.com/th/id/OIP.6vcza48zUnfQ2vUIxPM22AHaHa?cb=thvnext&w=500&h=500&rs=1&pid=ImgDetMain",
            ingredientes = listOf("Frutas vermelhas congeladas", "Banana", "Iogurte"),
            modoPreparo = listOf("Bata tudo no liquidificador."),
            tempoPreparo = "5 min",
            porcoes = 1,
            proteinas = 5.0f,
            carboidratos = 28.0f,
            gorduras = 2.0f,
            calorias = 140,
            fibras = 3.0f
        ),

        Receita(
            id = 4,
            nome = "Panqueca Vegana de Banana",
            descricaoCurta = "Panqueca saudável sem glúten, leite ou ovos.",
            imagemUrl = "https://naminhapanela.com/wp-content/uploads/2020/03/panqueca-de-banana-vegana-2-1900x1267.jpg",
            ingredientes = listOf(
                "1 banana madura amassada",
                "3 colheres de sopa de farinha de arroz",
                "2 colheres de sopa de água ou leite vegetal",
                "1 pitada de canela",
                "1 colher de chá de fermento (opcional)"
            ),
            modoPreparo = listOf(
                "Misture todos os ingredientes até formar uma massa homogênea.",
                "Aqueça uma frigideira antiaderente e despeje a massa.",
                "Cozinhe em fogo médio por 2 a 3 minutos de cada lado."
            ),
            tempoPreparo = "15 min",
            porcoes = 1,
            proteinas = 3.0f,
            carboidratos = 22.0f,
            gorduras = 1.5f,
            calorias = 110,
            fibras = 2.0f
        )
    )

    val listaDePerguntasFrequentes = listOf(
        "Como adicionar uma receita aos favoritos?",
        "Onde encontro as configurações do app?",
        "Como pesquisar por uma receita?"
    )
}
