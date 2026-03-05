package com.example.sqlliteteamapp.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class PersonalDetail(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dateOfBirth: String = "",
    val bio: String = "",
    val profession: String = "",
    val city: String = "",
    val country: String = "",
    val profilePhotoUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

class FirebaseHelper {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    companion object {
        private const val USERS_COLLECTION = "users"
        private const val PERSONAL_DETAILS_COLLECTION = "personal_details"
    }

    // Get current user ID
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    // Save personal details to Firebase
    suspend fun savePersonalDetails(details: PersonalDetail): Result<String> {
        return try {
            val userId = getCurrentUserId() ?: throw Exception("User not authenticated")
            val detailsWithUserId = details.copy(
                userId = userId,
                updatedAt = System.currentTimeMillis()
            )
            
            db.collection(USERS_COLLECTION)
                .document(userId)
                .collection(PERSONAL_DETAILS_COLLECTION)
                .document("profile")
                .set(detailsWithUserId)
                .await()
            
            Result.success("Personal details saved successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get personal details from Firebase
    suspend fun getPersonalDetails(): Result<PersonalDetail?> {
        return try {
            val userId = getCurrentUserId() ?: throw Exception("User not authenticated")
            
            val document = db.collection(USERS_COLLECTION)
                .document(userId)
                .collection(PERSONAL_DETAILS_COLLECTION)
                .document("profile")
                .get()
                .await()
            
            val personalDetail = document.toObject(PersonalDetail::class.java)
            Result.success(personalDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Check if user is authenticated
    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    // Anonymous sign-in (for testing without Firebase Auth UI)
    suspend fun signInAnonymously(): Result<String> {
        return try {
            auth.signInAnonymously().await()
            Result.success("Signed in anonymously")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Sign out
    suspend fun signOut(): Result<String> {
        return try {
            auth.signOut()
            Result.success("Signed out successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Delete personal details
    suspend fun deletePersonalDetails(): Result<String> {
        return try {
            val userId = getCurrentUserId() ?: throw Exception("User not authenticated")
            
            db.collection(USERS_COLLECTION)
                .document(userId)
                .collection(PERSONAL_DETAILS_COLLECTION)
                .document("profile")
                .delete()
                .await()
            
            Result.success("Personal details deleted")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Update a specific field
    suspend fun updatePersonalDetailField(field: String, value: Any): Result<String> {
        return try {
            val userId = getCurrentUserId() ?: throw Exception("User not authenticated")
            
            db.collection(USERS_COLLECTION)
                .document(userId)
                .collection(PERSONAL_DETAILS_COLLECTION)
                .document("profile")
                .update(field, value, "updatedAt", System.currentTimeMillis())
                .await()
            
            Result.success("Field updated successfully")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
