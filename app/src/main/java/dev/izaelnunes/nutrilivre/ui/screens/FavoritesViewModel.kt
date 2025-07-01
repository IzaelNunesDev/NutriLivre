package nutrilivre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.izaelnunes.nutrilivre.data.FavoritesRepository
import dev.izaelnunes.nutrilivre.model.DadosMockados
import dev.izaelnunes.nutrilivre.model.Receita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first


class FavoritesViewModel(private val repository: FavoritesRepository) : ViewModel() {
    private val _favoritos = MutableStateFlow<List<Receita>>(emptyList())
    val favoritos: StateFlow<List<Receita>> = _favoritos.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllFavorites().collectLatest { favoritosIds ->
                val receitasFavoritas = DadosMockados.listaDeReceitas.filter { receita ->
                    favoritosIds.any { it.receitaId == receita.id }
                }
                _favoritos.value = receitasFavoritas
            }
        }
    }

    fun toggleFavorito(receita: Receita) {
        viewModelScope.launch {
            val isFav = repository.isFavorite(receita.id).first()
            if (isFav) {
                repository.removeFavorite(receita.id)
            } else {
                repository.addFavorite(receita.id)
            }
        }
    }
}

class FavoritesViewModelFactory(private val repository: FavoritesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 