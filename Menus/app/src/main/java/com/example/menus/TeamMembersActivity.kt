package com.example.menus

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.menus.MemberDescriptionActivity
import com.example.menus.R

class TeamMembersActivity : AppCompatActivity() {

    private var selectedMemberId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_members)

        val member1 = findViewById<TextView>(R.id.member1)
        val member2 = findViewById<TextView>(R.id.member2)
        val member3 = findViewById<TextView>(R.id.member3)

        registerForContextMenu(member1)
        registerForContextMenu(member2)
        registerForContextMenu(member3)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        selectedMemberId = v?.id ?: 0
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.viewProfile) {
            val intent = Intent(this, MemberDescriptionActivity::class.java)
            intent.putExtra("memberId", selectedMemberId)
            startActivity(intent)
        }
        return true
    }
}
