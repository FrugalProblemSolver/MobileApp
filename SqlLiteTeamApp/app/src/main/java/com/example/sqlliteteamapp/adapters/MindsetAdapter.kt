package com.example.sqlliteteamapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.utils.MindsetPreferenceManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class MindsetAdapter(
    private val mindsets: List<MindsetPreferenceManager.Mindset>,
    private val currentMindset: MindsetPreferenceManager.Mindset,
    private val onItemClick: (MindsetPreferenceManager.Mindset) -> Unit
) : RecyclerView.Adapter<MindsetAdapter.ViewHolder>() {

    inner class ViewHolder(val card: CardView) : RecyclerView.ViewHolder(card) {
        private val mindsetEmoji: TextView = card.findViewById(R.id.mindsetEmoji)
        private val mindsetName: TextView = card.findViewById(R.id.mindsetName)

        fun bind(mindset: MindsetPreferenceManager.Mindset) {
            mindsetEmoji.text = mindset.emoji
            mindsetName.text = mindset.displayName

            // Highlight current mindset
            if (mindset == currentMindset) {
                card.setCardBackgroundColor(card.context.getColor(R.color.purple_200))
                mindsetName.setTextColor(card.context.getColor(R.color.purple_700))
            } else {
                card.setCardBackgroundColor(card.context.getColor(R.color.white))
                mindsetName.setTextColor(card.context.getColor(R.color.black))
            }

            card.setOnClickListener { onItemClick(mindset) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val card = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mindset, parent, false) as CardView
        return ViewHolder(card)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mindsets[position])
    }

    override fun getItemCount(): Int = mindsets.size;
}
