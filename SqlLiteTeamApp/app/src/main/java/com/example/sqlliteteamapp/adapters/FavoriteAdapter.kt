package com.example.sqlliteteamapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.models.Favorite
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.RatingBar

class FavoriteAdapter(
    private val onDelete: (Favorite) -> Unit
) : ListAdapter<Favorite, FavoriteAdapter.ViewHolder>(FavoriteDiffCallback()) {

    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container) {
        private val favoriteIcon: TextView = container.findViewById(R.id.favoriteIcon)
        private val favoriteTitle: TextView = container.findViewById(R.id.favoriteTitle)
        private val favoriteInfo: TextView = container.findViewById(R.id.favoriteInfo)
        private val favoriteRating: RatingBar = container.findViewById(R.id.favoriteRating)

        fun bind(favorite: Favorite) {
            // Set icon based on type
            favoriteIcon.text = if (favorite.type == "song") "🎵" else "🎬"

            favoriteTitle.text = favorite.title
            
            // Set info based on type
            val info = if (favorite.type == "song") {
                "${favorite.artist} • ${favorite.genre}"
            } else {
                "${favorite.genre} • ${favorite.releaseYear}"
            }
            favoriteInfo.text = info

            // Set rating
            favoriteRating.rating = favorite.rating

            // Delete on long click
            container.setOnLongClickListener {
                onDelete(favorite)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false) as LinearLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class FavoriteDiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }
}
