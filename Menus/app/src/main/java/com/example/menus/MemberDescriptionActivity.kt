package com.example.menus

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.menus.R

class MemberDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_description)

        val memberId = intent.getIntExtra("memberId", R.id.member1)

        val imageView = findViewById<ImageView>(R.id.memberImage)
        val nameText = findViewById<TextView>(R.id.memberName)
        val roleText = findViewById<TextView>(R.id.memberRole)
        val descriptionText = findViewById<TextView>(R.id.memberDescription)

        when (memberId) {
            R.id.member1 -> {
                nameText.text = "Soundar Arunachalam R M"
                roleText.text = "AIR 1 GATE 2026 • BOS of IT 2027"
                descriptionText.text = "Solves Leetcode in ASM • Exceptional competitive programmer and coding enthusiast"
                imageView.setImageResource(R.drawable.rmsoundar)
            }
            R.id.member2 -> {
                nameText.text = "Sethupathy R"
                roleText.text = "Vibe Coder • Professional Procrastinator"
                descriptionText.text = "All Style, No Substance • Creative coder with unique programming approach"
                imageView.setImageResource(R.drawable.sethupathyr)
            }
            R.id.member3 -> {
                nameText.text = "Suhas K S"
                roleText.text = "Leetcode Guardian • Upcoming Amazon Employee"
                descriptionText.text = "Claims to be a joker (he's not) • Dedicated problem solver and future Amazon engineer"
                imageView.setImageResource(R.drawable.suhasks)
            }
        }
    }
}
