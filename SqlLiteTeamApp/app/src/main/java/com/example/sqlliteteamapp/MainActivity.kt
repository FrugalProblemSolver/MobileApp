package com.example.sqlliteteamapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.sqlliteteamapp.ui.FavoritesActivity
import com.example.sqlliteteamapp.ui.MindsetActivity
import com.example.sqlliteteamapp.ui.PersonalDetailsActivity
import com.example.sqlliteteamapp.ui.TeamMembersActivity
import com.example.sqlliteteamapp.utils.FirebaseHelper
import com.example.sqlliteteamapp.utils.MindsetPreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var mindsetManager: MindsetPreferenceManager
    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize managers
        mindsetManager = MindsetPreferenceManager(this)
        firebaseHelper = FirebaseHelper()

        // Initialize Firebase (sign in anonymously if not authenticated)
        if (!firebaseHelper.isUserAuthenticated()) {
            // Optional: Sign in anonymously for testing
            // Can be skipped if you want to use other auth methods
        }

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        // Update mindset display
        updateMindsetDisplay()
    }

    private fun updateMindsetDisplay() {
        val mindsetDisplay = findViewById<TextView>(R.id.mindsetDisplay)
        val currentMindset = mindsetManager.getCurrentMindset()
        val emoji = currentMindset.emoji
        val displayName = currentMindset.displayName
        mindsetDisplay.text = "$emoji $displayName"
    }

    private fun setupClickListeners() {
        val updateMindsetBtn = findViewById<Button>(R.id.updateMindsetBtn)
        val teamMembersCard = findViewById<CardView>(R.id.teamMembersCard)
        val favoritesCard = findViewById<CardView>(R.id.favoritesCard)
        val personalDetailsCard = findViewById<CardView>(R.id.personalDetailsCard)

        updateMindsetBtn.setOnClickListener {
            startActivity(Intent(this, MindsetActivity::class.java))
        }

        teamMembersCard.setOnClickListener {
            startActivity(Intent(this, TeamMembersActivity::class.java))
        }

        favoritesCard.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        personalDetailsCard.setOnClickListener {
            startActivity(Intent(this, PersonalDetailsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Update mindset display every time we return to main activity
        updateMindsetDisplay()
    }
}