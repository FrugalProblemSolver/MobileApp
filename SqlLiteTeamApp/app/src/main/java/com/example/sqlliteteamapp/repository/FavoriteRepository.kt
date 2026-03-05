package com.example.sqlliteteamapp.repository

import com.example.sqlliteteamapp.database.FavoriteDao
import com.example.sqlliteteamapp.models.Favorite
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    
    // Add a new favorite
    suspend fun addFavorite(favorite: Favorite): Long {
        return favoriteDao.insertFavorite(favorite)
    }

    // Update an existing favorite
    suspend fun updateFavorite(favorite: Favorite) {
        favoriteDao.updateFavorite(favorite)
    }

    // Delete a favorite
    suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }

    // Delete favorite by ID
    suspend fun deleteFavoriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }

    // Get a favorite by ID
    suspend fun getFavoriteById(id: Int): Favorite? {
        return favoriteDao.getFavoriteById(id)
    }

    // Get all favorites as Flow
    fun getAllFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    // Get favorites by type (song or movie)
    fun getFavoritesByType(type: String): Flow<List<Favorite>> {
        return favoriteDao.getFavoritesByType(type)
    }

    // Search favorites
    fun searchFavorites(query: String): Flow<List<Favorite>> {
        return favoriteDao.searchFavorites(query)
    }

    // Get favorite count
    fun getFavoriteCount(): Flow<Int> {
        return favoriteDao.getFavoriteCount()
    }

    // Add song
    suspend fun addSong(title: String, artist: String, genre: String, rating: Float = 0f): Long {
        return addFavorite(
            Favorite(
                type = "song",
                title = title,
                artist = artist,
                genre = genre,
                rating = rating
            )
        )
    }

    // Add movie
    suspend fun addMovie(title: String, genre: String, releaseYear: Int, rating: Float = 0f): Long {
        return addFavorite(
            Favorite(
                type = "movie",
                title = title,
                genre = genre,
                releaseYear = releaseYear,
                rating = rating
            )
        )
    }

    // Get all songs
    fun getAllSongs(): Flow<List<Favorite>> {
        return getFavoritesByType("song")
    }

    // Get all movies
    fun getAllMovies(): Flow<List<Favorite>> {
        return getFavoritesByType("movie")
    }
}
