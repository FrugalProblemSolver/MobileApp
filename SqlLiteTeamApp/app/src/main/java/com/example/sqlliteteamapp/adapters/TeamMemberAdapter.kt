package com.example.sqlliteteamapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.models.TeamMember
import androidx.appcompat.widget.AppCompatImageView
import android.widget.LinearLayout
import android.widget.TextView

class TeamMemberAdapter(
    private val members: List<TeamMember>,
    private val onItemClick: (TeamMember) -> Unit
) : RecyclerView.Adapter<TeamMemberAdapter.ViewHolder>() {

    inner class ViewHolder(val container: LinearLayout) : RecyclerView.ViewHolder(container) {
        private val memberName: TextView = container.findViewById(R.id.memberName)
        private val memberTitle: TextView = container.findViewById(R.id.memberTitle)
        private val memberDescription: TextView = container.findViewById(R.id.memberDescription)

        fun bind(member: TeamMember) {
            memberName.text = member.name
            memberTitle.text = member.title
            memberDescription.text = member.description
            container.setOnClickListener { onItemClick(member) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team_member, parent, false) as LinearLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int = members.size
}
