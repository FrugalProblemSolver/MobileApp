package com.example.menus


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.menus.TeamMembersActivity
import com.example.menus.AboutUsActivity
import com.example.menus.ProjectDescriptionActivity
import com.example.menus.R
import com.example.menus.TeamDetailsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val popupBtn = findViewById<Button>(R.id.popupBtn)
        popupBtn.setOnClickListener {
            showPopupMenu(it)
        }
    }

    // OPTIONS MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> startActivity(Intent(this, AboutUsActivity::class.java))
            R.id.teamDetails -> startActivity(Intent(this, TeamDetailsActivity::class.java))
            R.id.teamMembers -> startActivity(Intent(this, TeamMembersActivity::class.java))
            R.id.projectDesc -> startActivity(Intent(this, ProjectDescriptionActivity::class.java))
        }
        return true
    }

    // POPUP MENU
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.about ->
                    startActivity(Intent(this, AboutUsActivity::class.java))

                R.id.teamDetails ->
                    startActivity(Intent(this, TeamDetailsActivity::class.java))

                R.id.teamMembers ->
                    startActivity(Intent(this, TeamMembersActivity::class.java))

                R.id.projectDesc ->
                    startActivity(Intent(this, ProjectDescriptionActivity::class.java))
            }
            true
        }

        popup.show()
    }
}
