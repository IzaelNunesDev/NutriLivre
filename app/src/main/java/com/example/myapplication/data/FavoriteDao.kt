package nutrilivre.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteReceitaId)

    @Delete
    suspend fun delete(favorite: FavoriteReceitaId)

    @Query("SELECT * FROM favorite_receitas")
    fun getAllFavorites(): Flow<List<FavoriteReceitaId>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_receitas WHERE receitaId = :id)")
    fun isFavorite(id: Int): Flow<Boolean>
} 