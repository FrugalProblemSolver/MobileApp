package com.example.menus

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.menus.models.BirthdayEvent
import com.example.menus.repositories.BirthdayRepository
import com.example.menus.workers.BirthdayNotificationWorker
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.Calendar
import kotlin.math.abs

class BirthdayActivity : AppCompatActivity() {

    private lateinit var birthdayRepository: BirthdayRepository
    private lateinit var memberSpinner: Spinner
    private lateinit var birthdayListView: ListView
    private lateinit var progressBar: ProgressBar
    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null

    private val teamMembers = mapOf(
        0 to "Soundar Arunachalam R M",
        1 to "Sethupathy R",
        2 to "Suhas K S"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday)

        supportActionBar?.title = "Birthday Management"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        birthdayRepository = BirthdayRepository(this)

        // Initialize UI components
        initializeViews()

        // Set up member spinner
        setupMemberSpinner()

        // Load existing birthdays
        loadBirthdays()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeViews() {
        memberSpinner = findViewById(R.id.memberSpinner)
        birthdayListView = findViewById(R.id.birthdayListView)
        progressBar = findViewById(R.id.progressBar)

        val addDateButton: Button = findViewById(R.id.addDateButton)
        val addTimeButton: Button = findViewById(R.id.addTimeButton)
        val saveBirthdayButton: Button = findViewById(R.id.saveBirthdayButton)
        val deleteAllButton: Button = findViewById(R.id.deleteAllButton)

        addDateButton.setOnClickListener { showDatePicker() }
        addTimeButton.setOnClickListener { showTimePicker() }
        saveBirthdayButton.setOnClickListener { saveBirthday() }
        deleteAllButton.setOnClickListener { deleteAllBirthdays() }
    }

    private fun setupMemberSpinner() {
        val memberNames = teamMembers.values.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, memberNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        memberSpinner.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            Toast.makeText(
                this,
                "Date selected: $selectedDate",
                Toast.LENGTH_SHORT
            ).show()
        }, year, month, day).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            selectedTime = LocalTime.of(selectedHour, selectedMinute)
            Toast.makeText(
                this,
                "Time selected: ${String.format("%02d:%02d", selectedHour, selectedMinute)}",
                Toast.LENGTH_SHORT
            ).show()
        }, hour, minute, true).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveBirthday() {
        if (selectedDate == null || selectedTime == null) {
            Toast.makeText(
                this,
                "Please select both date and time",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        progressBar.visibility = ProgressBar.VISIBLE

        val selectedMemberIndex = memberSpinner.selectedItemPosition
        val memberName = teamMembers[selectedMemberIndex] ?: return
        val memberId = selectedMemberIndex + 1

        val birthdayEvent = BirthdayEvent(
            id = System.currentTimeMillis().toInt(),
            memberId = memberId,
            memberName = memberName,
            date = selectedDate!!,
            time = selectedTime!!,
            greetingMessage = "🎉 Happy Birthday, $memberName! 🎂"
        )

        // Save to repository
        birthdayRepository.addBirthday(birthdayEvent)

        // Schedule notification
        scheduleBirthdayNotification(birthdayEvent)

        // Reset selections
        selectedDate = null
        selectedTime = null

        progressBar.visibility = ProgressBar.GONE

        Toast.makeText(
            this,
            "Birthday saved for $memberName",
            Toast.LENGTH_SHORT
        ).show()

        loadBirthdays()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleBirthdayNotification(birthday: BirthdayEvent) {
        // Calculate the next occurrence of this birthday
        val today = LocalDate.now()
        var birthdayThisYear = birthday.date.withYear(today.year)

        // If the birthday has already passed this year, schedule for next year
        if (birthdayThisYear.isBefore(today)) {
            birthdayThisYear = birthday.date.withYear(today.year + 1)
        }

        // Combine date and time
        val birthdayDateTime = birthdayThisYear.atTime(birthday.time)
        val zonedDateTime = birthdayDateTime.atZone(java.time.ZoneId.systemDefault())

        // Calculate delay in milliseconds
        val now = ZonedDateTime.now()
        val delayMs = abs(java.time.Duration.between(now, zonedDateTime).toMillis())

        // Create work request with input data
        val inputData = Data.Builder()
            .putString("memberName", birthday.memberName)
            .putInt("memberId", birthday.memberId)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<BirthdayNotificationWorker>()
            .setInitialDelay(delayMs, java.util.concurrent.TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadBirthdays() {
        progressBar.visibility = ProgressBar.VISIBLE

        try {
            val birthdays = birthdayRepository.getAllBirthdays()

            val birthdayStrings = birthdays.map { birthday ->
                val dateStr = "${birthday.date.dayOfMonth}/${birthday.date.monthValue}/${birthday.date.year}"
                val timeStr = String.format("%02d:%02d", birthday.time.hour, birthday.time.minute)
                "${birthday.memberName}\n$dateStr @ $timeStr"
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                birthdayStrings
            )
            birthdayListView.adapter = adapter

            // Set up delete on item click
            birthdayListView.setOnItemClickListener { _, _, position, _ ->
                val birthday = birthdays[position]
                deleteBirthday(birthday)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading birthdays: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            progressBar.visibility = ProgressBar.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteBirthday(birthday: BirthdayEvent) {
        birthdayRepository.deleteBirthday(birthday.id)
        Toast.makeText(
            this,
            "Birthday deleted for ${birthday.memberName}",
            Toast.LENGTH_SHORT
        ).show()
        loadBirthdays()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteAllBirthdays() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Delete All Birthdays")
            .setMessage("Are you sure you want to delete all birthdays?")
            .setPositiveButton("Yes") { _, _ ->
                birthdayRepository.clearAllBirthdays()
                WorkManager.getInstance(this).cancelAllWork() // Cancel all birthday notifications
                Toast.makeText(this, "All birthdays deleted", Toast.LENGTH_SHORT).show()
                loadBirthdays()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
