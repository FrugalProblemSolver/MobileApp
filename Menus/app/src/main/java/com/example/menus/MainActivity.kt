package com.example.menus


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.menus.TeamMembersActivity
import com.example.menus.AboutUsActivity
import com.example.menus.BirthdayActivity
import com.example.menus.ProjectDescriptionActivity
import com.example.menus.R
import com.example.menus.TeamDetailsActivity
import com.example.menus.workers.StatusNotificationWorker
import com.example.menus.utils.NotificationHelper
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize notification channels
        NotificationHelper.createNotificationChannels(this)

        // Schedule hourly status notifications
        scheduleHourlyNotifications()

        val popupBtn = findViewById<Button>(R.id.popupBtn)
        popupBtn.setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun scheduleHourlyNotifications() {
        val statusWorkRequest = PeriodicWorkRequestBuilder<StatusNotificationWorker>(
            1, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "hourly_status",
            ExistingPeriodicWorkPolicy.KEEP,
            statusWorkRequest
        )
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
            R.id.birthdays -> startActivity(Intent(this, BirthdayActivity::class.java))
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

                R.id.birthdays ->
                    startActivity(Intent(this, BirthdayActivity::class.java))
            }
            true
        }

        popup.show()
    }

    // EXIT ALERT DIALOG
    override fun onBackPressed() {
        super.onBackPressed()
        AlertDialog.Builder(this)
            .setTitle("Exit Application")
            .setMessage("Are you sure you want to exit the HumbleHackers app?")
            .setPositiveButton("Yes") { _, _ ->
                // Cancel all background notifications
                WorkManager.getInstance(this).cancelAllWork()
                // Close the app
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

