package com.example.sqlliteteamapp.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.models.TeamMembersData

class MemberDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_detail)

        val memberId = intent.getIntExtra("memberId", 1)
        val member = TeamMembersData.getMemberById(memberId)

        if (member != null) {
            setupUI(member)
        }
    }

    private fun setupUI(member: com.example.sqlliteteamapp.models.TeamMember) {
        findViewById<ImageButton>(R.id.backBtn).setOnClickListener { finish() }

        findViewById<TextView>(R.id.memberFullName).text = member.name
        findViewById<TextView>(R.id.memberDetailTitle).text = member.title
        findViewById<TextView>(R.id.memberBio).text = member.bio
        findViewById<TextView>(R.id.memberEmail).text = "📧 ${member.email}"
        findViewById<TextView>(R.id.memberPhone).text = "📱 ${member.phone}"
        findViewById<TextView>(R.id.memberDOB).text = "🎂 ${member.dob}"
        findViewById<TextView>(R.id.memberDescription).text = member.description
    }
}
