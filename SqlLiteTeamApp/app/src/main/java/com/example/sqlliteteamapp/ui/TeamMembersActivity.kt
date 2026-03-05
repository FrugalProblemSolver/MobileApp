package com.example.sqlliteteamapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.adapters.TeamMemberAdapter
import com.example.sqlliteteamapp.models.TeamMembersData

class TeamMembersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_members)

        // Setup toolbar
        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener { finish() }

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.teamRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val adapter = TeamMemberAdapter(TeamMembersData.members) { member ->
            val intent = Intent(this, MemberDetailActivity::class.java)
            intent.putExtra("memberId", member.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
