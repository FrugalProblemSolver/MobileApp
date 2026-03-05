package com.example.sqlliteteamapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String, // "song" or "movie"
    val title: String,
    val artist: String = "", // For songs
    val genre: String = "",
    val releaseYear: Int = 0,
    val rating: Float = 0f,
    val description: String = "",
    val dateAdded: Long = System.currentTimeMillis()
)
