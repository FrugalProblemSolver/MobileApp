package com.example.menus.repositories

import android.content.Context
import android.content.SharedPreferences
import com.example.menus.models.BirthdayEvent
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class BirthdayRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("birthdays", Context.MODE_PRIVATE)

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    companion object {
        private const val BIRTHDAYS_KEY = "birthdays"
    }

    fun addBirthday(birthday: BirthdayEvent) {
        val birthdays = getAllBirthdays().toMutableList()
        
        // Remove if already exists
        birthdays.removeAll { it.id == birthday.id }
        
        // Add new birthday
        birthdays.add(birthday)
        
        saveBirthdays(birthdays)
    }

    fun getAllBirthdays(): List<BirthdayEvent> {
        val jsonString = sharedPreferences.getString(BIRTHDAYS_KEY, "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        
        val birthdays = mutableListOf<BirthdayEvent>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            birthdays.add(parseBirthday(jsonObject))
        }
        
        return birthdays.sortedBy { it.date }
    }

    fun getBirthdayById(id: Int): BirthdayEvent? {
        return getAllBirthdays().find { it.id == id }
    }

    fun deleteBirthday(id: Int) {
        val birthdays = getAllBirthdays().toMutableList()
        birthdays.removeAll { it.id == id }
        saveBirthdays(birthdays)
    }

    fun updateBirthday(birthday: BirthdayEvent) {
        deleteBirthday(birthday.id)
        addBirthday(birthday)
    }

    fun clearAllBirthdays() {
        sharedPreferences.edit().remove(BIRTHDAYS_KEY).apply()
    }

    private fun saveBirthdays(birthdays: List<BirthdayEvent>) {
        val jsonArray = JSONArray()
        
        for (birthday in birthdays) {
            jsonArray.put(birthdayToJson(birthday))
        }
        
        sharedPreferences.edit()
            .putString(BIRTHDAYS_KEY, jsonArray.toString())
            .apply()
    }

    private fun birthdayToJson(birthday: BirthdayEvent): JSONObject {
        return JSONObject().apply {
            put("id", birthday.id)
            put("memberId", birthday.memberId)
            put("memberName", birthday.memberName)
            put("date", birthday.date.format(dateFormatter))
            put("time", birthday.time.format(timeFormatter))
            put("greetingMessage", birthday.greetingMessage)
        }
    }

    private fun parseBirthday(jsonObject: JSONObject): BirthdayEvent {
        return BirthdayEvent(
            id = jsonObject.getInt("id"),
            memberId = jsonObject.getInt("memberId"),
            memberName = jsonObject.getString("memberName"),
            date = LocalDate.parse(jsonObject.getString("date"), dateFormatter),
            time = LocalTime.parse(jsonObject.getString("time"), timeFormatter),
            greetingMessage = jsonObject.getString("greetingMessage")
        )
    }
}
