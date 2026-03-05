package com.example.sqlliteteamapp.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.adapters.MindsetAdapter
import com.example.sqlliteteamapp.utils.MindsetPreferenceManager

class MindsetActivity : AppCompatActivity() {

    private lateinit var mindsetManager: MindsetPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mindset)

        mindsetManager = MindsetPreferenceManager(this)

        // Setup toolbar
        findViewById<ImageButton>(R.id.backBtn).setOnClickListener { finish() }

        // Setup RecyclerView with grid layout (2 columns)
        val recyclerView = findViewById<RecyclerView>(R.id.mindsetRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val currentMindset = mindsetManager.getCurrentMindset()
        val adapter = MindsetAdapter(mindsetManager.getAllMindsets(), currentMindset) { mindset ->
            mindsetManager.setMindset(mindset)
            Toast.makeText(this, "${mindset.emoji} ${mindset.displayName} selected!", Toast.LENGTH_SHORT).show()
            // Refresh adapter to update selection
            recyclerView.adapter = MindsetAdapter(mindsetManager.getAllMindsets(), mindset) {}
        }
        recyclerView.adapter = adapter
    }
}
