package com.example.sqlliteteamapp.models

data class TeamMember(
    val id: Int,
    val name: String,
    val title: String,
    val description: String,
    val imageUrl: String = "",
    val email: String = "",
    val phone: String = "",
    val dob: String = "", // Format: MM-DD
    val bio: String = ""
)

// Default team members
object TeamMembersData {
    val members = listOf(
        TeamMember(
            id = 1,
            name = "Soundar Arunachalam R M",
            title = "AIR 1 GATE 2026 • BOS of IT 2027",
            description = "Solves Leetcode in ASM - Exceptional competitive programmer",
            email = "soundar@example.com",
            phone = "+91 98765 43210",
            dob = "05-15",
            bio = "Passionate about algorithms and competitive programming. Expert in breaking down complex problems."
        ),
        TeamMember(
            id = 2,
            name = "Sethupathy R",
            title = "Vibe Coder • Professional Procrastinator",
            description = "All Style, No Substance - Creative coder with unique approach",
            email = "sethupathy@example.com",
            phone = "+91 98765 43211",
            dob = "08-22",
            bio = "Creative developer with out-of-the-box thinking. Brings style and innovation to every project."
        ),
        TeamMember(
            id = 3,
            name = "Suhas K S",
            title = "Leetcode Guardian • Upcoming Amazon Employee",
            description = "Claims to be a joker (he's not) - Dedicated problem solver",
            email = "suhas@example.com",
            phone = "+91 98765 43212",
            dob = "03-18",
            bio = "Dedicated to mastering data structures and algorithms. Always ready to solve challenging problems."
        )
    )

    fun getMemberById(id: Int): TeamMember? = members.find { it.id == id }
}
