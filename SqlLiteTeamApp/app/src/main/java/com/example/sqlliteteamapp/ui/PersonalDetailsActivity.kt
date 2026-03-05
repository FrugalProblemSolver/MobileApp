package com.example.sqlliteteamapp.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sqlliteteamapp.R
import com.example.sqlliteteamapp.utils.FirebaseHelper
import com.example.sqlliteteamapp.utils.PersonalDetail
import kotlinx.coroutines.launch

class PersonalDetailsActivity : AppCompatActivity() {

    private lateinit var firebaseHelper: FirebaseHelper
    private lateinit var nameEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var phoneEdit: EditText
    private lateinit var professionEdit: EditText
    private lateinit var cityEdit: EditText
    private lateinit var bioEdit: EditText
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_details)

        firebaseHelper = FirebaseHelper()

        // Setup toolbar
        findViewById<ImageButton>(R.id.backBtn).setOnClickListener { finish() }

        // Initialize views
        nameEdit = findViewById(R.id.nameEdit)
        emailEdit = findViewById(R.id.emailEdit)
        phoneEdit = findViewById(R.id.phoneEdit)
        professionEdit = findViewById(R.id.professionEdit)
        cityEdit = findViewById(R.id.cityEdit)
        bioEdit = findViewById(R.id.bioEdit)
        statusText = findViewById(R.id.statusText)

        // Setup buttons
        findViewById<Button>(R.id.saveBtn).setOnClickListener { savePersonalDetails() }
        findViewById<Button>(R.id.loadBtn).setOnClickListener { loadPersonalDetails() }

        // Load details on startup
        loadPersonalDetails()
    }

    private fun savePersonalDetails() {
        val details = PersonalDetail(
            name = nameEdit.text.toString(),
            email = emailEdit.text.toString(),
            phone = phoneEdit.text.toString(),
            profession = professionEdit.text.toString(),
            city = cityEdit.text.toString(),
            bio = bioEdit.text.toString()
        )

        lifecycleScope.launch {
            val result = firebaseHelper.savePersonalDetails(details)
            result.onSuccess {
                statusText.text = "✅ Saved to Firebase!"
                Toast.makeText(this@PersonalDetailsActivity, it, Toast.LENGTH_SHORT).show()
            }
            result.onFailure { error ->
                statusText.text = "❌ Error: ${error.message}"
                Toast.makeText(this@PersonalDetailsActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadPersonalDetails() {
        statusText.text = "⏳ Loading from Firebase..."

        lifecycleScope.launch {
            val result = firebaseHelper.getPersonalDetails()
            result.onSuccess { details ->
                if (details != null) {
                    nameEdit.setText(details.name)
                    emailEdit.setText(details.email)
                    phoneEdit.setText(details.phone)
                    professionEdit.setText(details.profession)
                    cityEdit.setText(details.city)
                    bioEdit.setText(details.bio)
                    statusText.text = "✅ Loaded from Firebase!"
                } else {
                    statusText.text = "📝 No saved details found"
                }
            }
            result.onFailure { error ->
                statusText.text = "❌ Error: ${error.message}"
                Toast.makeText(this@PersonalDetailsActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
