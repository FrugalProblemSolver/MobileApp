package com.example.sqlliteteamapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sqlliteteamapp.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    
    @Insert
    suspend fun insertFavorite(favorite: Favorite): Long
    
    @Update
    suspend fun updateFavorite(favorite: Favorite)
    
    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
    
    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getFavoriteById(id: Int): Favorite?
    
    @Query("SELECT * FROM favorites ORDER BY dateAdded DESC")
    fun getAllFavorites(): Flow<List<Favorite>>
    
    @Query("SELECT * FROM favorites WHERE type = :type ORDER BY dateAdded DESC")
    fun getFavoritesByType(type: String): Flow<List<Favorite>>
    
    @Query("SELECT * FROM favorites WHERE title LIKE '%' || :query || '%' OR artist LIKE '%' || :query || '%'")
    fun searchFavorites(query: String): Flow<List<Favorite>>
    
    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
    
    @Query("SELECT COUNT(*) FROM favorites")
    fun getFavoriteCount(): Flow<Int>
}
