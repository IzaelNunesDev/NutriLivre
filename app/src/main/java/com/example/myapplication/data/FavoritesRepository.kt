package nutrilivre.data

import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val dao: FavoriteDao) {
    fun getAllFavorites(): Flow<List<FavoriteReceitaId>> = dao.getAllFavorites()
    fun isFavorite(id: Int): Flow<Boolean> = dao.isFavorite(id)
    suspend fun addFavorite(id: Int) = dao.insert(FavoriteReceitaId(id))
    suspend fun removeFavorite(id: Int) = dao.delete(FavoriteReceitaId(id))
} 