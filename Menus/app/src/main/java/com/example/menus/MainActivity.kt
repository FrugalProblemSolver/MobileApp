package com.example.menus

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnContext = findViewById<Button>(R.id.btn_context)
        val btnPopup = findViewById<Button>(R.id.btn_popup)

        // 1. REGISTER for Context Menu (Triggered by long-press)
        registerForContextMenu(btnContext)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)

        // 2. Set it as the Support Action Bar
        // This tells Android: "Use this toolbar for my Options Menu"
        setSupportActionBar(toolbar)
        // 2. TRIGGER for Popup Menu (Triggered by standard click)
        btnPopup.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    // --- OPTIONS MENU (Top Right App Bar) ---
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showToast("logout Selected")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // --- CONTEXT MENU (Floating menu on long-press) ---
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.navigation_menu, menu)
        menu?.setHeaderTitle("Context Action")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_aboutUs -> {
                showToast("aboutUs Selected")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    // --- POPUP MENU (Anchored to a specific View) ---
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.navigation_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            showToast("Clicked: ${item.title}")
            true
        }
        popup.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}