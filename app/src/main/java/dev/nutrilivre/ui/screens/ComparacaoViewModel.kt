package dev.nutrilivre.ui.screens

import androidx.lifecycle.ViewModel
import dev.nutrilivre.model.Receita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ComparacaoViewModel : ViewModel() {
    private val _selecionadas = MutableStateFlow<List<Receita>>(emptyList())
    val selecionadas: StateFlow<List<Receita>> = _selecionadas.asStateFlow()

    fun adicionarReceita(receita: Receita) {
        if (_selecionadas.value.size < 4 && !_selecionadas.value.contains(receita)) {
            _selecionadas.value = _selecionadas.value + receita
        }
    }

    fun removerReceita(receita: Receita) {
        _selecionadas.value = _selecionadas.value - receita
    }

    fun limparSelecao() {
        _selecionadas.value = emptyList()
    }
} 