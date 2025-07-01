package dev.nutrilivre.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_receitas")
data class FavoriteReceitaId(
    @PrimaryKey val receitaId: Int
) 