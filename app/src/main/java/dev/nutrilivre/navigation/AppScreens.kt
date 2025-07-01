package dev.nutrilivre.navigation

object AppScreens {
    const val TelaInicialScreen = "tela_inicial"
    const val FavoritosScreen = "favoritos"
    const val ConfiguracoesScreen = "configuracoes"
    const val AjudaScreen = "ajuda"
    const val BuscaScreen = "busca"
    const val DetalheScreen = "detalhe_receita/{receitaId}"

    fun createDetalheRoute(receitaId: Int): String {
        return "detalhe_receita/$receitaId"
    }
}