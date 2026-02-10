package com.example.menus.models

import java.time.LocalDate
import java.time.LocalTime

data class BirthdayEvent(
    val id: Int,
    val memberId: Int,  // R.id.member1, member2, member3 etc
    val memberName: String,
    val date: LocalDate,  // Birthday date (Month, Day)
    val time: LocalTime,  // Notification time (e.g., 9:00 AM)
    val greetingMessage: String = "🎉 Happy Birthday, $memberName! 🎂"
) {
    override fun toString(): String {
        return "$memberName - $date at ${time.hour}:${String.format("%02d", time.minute)}"
    }
}
