package com.example.sqlliteteamapp.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.adapters.FavoriteAdapter
import com.example.sqlliteteamapp.database.TeamAppDatabase
import com.example.sqlliteteamapp.models.Favorite
import com.example.sqlliteteamapp.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        // Initialize database and repository
        val database = TeamAppDatabase.getDatabase(this)
        favoriteRepository = FavoriteRepository(database.favoriteDao())

        // Setup toolbar
        findViewById<ImageButton>(R.id.backBtn).setOnClickListener { finish() }

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.favoritesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter { favorite ->
            // Delete on item click
            lifecycleScope.launch {
                favoriteRepository.deleteFavorite(favorite)
            }
        }
        recyclerView.adapter = favoriteAdapter

        // Setup add button
        val addBtn = findViewById<Button>(R.id.addFavoriteBtn)
        addBtn.setOnClickListener {
            showAddFavoriteDialog()
        }

        // Load favorites
        loadFavorites()
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            favoriteRepository.getAllFavorites().collect { favorites ->
                favoriteAdapter.submitList(favorites)
            }
        }
    }

    private fun showAddFavoriteDialog() {
        // Simple dialog for demo - can be improved
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Add to Favorites")
            .setPositiveButton("Cancel", null)
            .create()
        dialog.show()

        // For now, add a sample favorite
        lifecycleScope.launch {
            favoriteRepository.addSong("Sample Song", "Artist Name", "Pop", 4.5f)
            loadFavorites()
        }
    }
}
