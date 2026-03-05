package com.example.sqlliteteamapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sqlliteteamapp.models.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class TeamAppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: TeamAppDatabase? = null

        fun getDatabase(context: Context): TeamAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TeamAppDatabase::class.java,
                    "team_app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
