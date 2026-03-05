package com.example.sqlliteteamapp.utils

import android.content.Context
import android.content.SharedPreferences

class MindsetPreferenceManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "team_app_prefs"
        private const val KEY_MINDSET = "current_mindset"
        private const val KEY_MINDSET_TIMESTAMP = "mindset_timestamp"
        private const val KEY_MINDSET_HISTORY = "mindset_history" // JSON array
    }

    enum class Mindset(val displayName: String, val emoji: String) {
        HAPPY("Happy", "😊"),
        SAD("Sad", "😢"),
        LONELY("Lonely", "😔"),
        EXCITED("Excited", "🤩"),
        CALM("Calm", "😌"),
        FOCUSED("Focused", "🎯"),
        CONFUSED("Confused", "😕"),
        GRATEFUL("Grateful", "🙏"),
        TIRED("Tired", "😴"),
        MOTIVATED("Motivated", "💪")
    }

    // Get current mindset
    fun getCurrentMindset(): Mindset {
        val mindsetName = sharedPreferences.getString(KEY_MINDSET, Mindset.HAPPY.name) ?: Mindset.HAPPY.name
        return try {
            Mindset.valueOf(mindsetName)
        } catch (e: IllegalArgumentException) {
            Mindset.HAPPY
        }
    }

    // Set current mindset
    fun setMindset(mindset: Mindset) {
        sharedPreferences.edit().apply {
            putString(KEY_MINDSET, mindset.name)
            putLong(KEY_MINDSET_TIMESTAMP, System.currentTimeMillis())
            apply()
        }
    }

    // Get mindset timestamp (when it was last set)
    fun getMindsetTimestamp(): Long {
        return sharedPreferences.getLong(KEY_MINDSET_TIMESTAMP, 0)
    }

    // Get how long current mindset has been active (in minutes)
    fun getMindsetDuration(): Long {
        val timestamp = getMindsetTimestamp()
        return if (timestamp > 0) {
            (System.currentTimeMillis() - timestamp) / (1000 * 60) // Convert to minutes
        } else {
            0
        }
    }

    // Check if mindset is from today
    fun isMindsetFromToday(): Boolean {
        val timestamp = getMindsetTimestamp()
        val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val mindsetDate = timestamp / (1000 * 60 * 60 * 24)
        return today == mindsetDate
    }

    // Get all mindsets
    fun getAllMindsets(): List<Mindset> {
        return Mindset.values().toList()
    }

    // Clear mindset (return to default)
    fun clearMindset() {
        sharedPreferences.edit().apply {
            putString(KEY_MINDSET, Mindset.HAPPY.name)
            putLong(KEY_MINDSET_TIMESTAMP, 0)
            apply()
        }
    }

    // Format mindset info as string
    fun getMindsetInfo(): String {
        val mindset = getCurrentMindset()
        val duration = getMindsetDuration()
        val unit = if (duration > 60) "hours" else "minutes"
        val value = if (duration > 60) duration / 60 else duration
        
        return if (duration > 0) {
            "$${mindset.emoji} ${mindset.displayName} for $value $unit"
        } else {
            "${mindset.emoji} ${mindset.displayName}"
        }
    }
}
