# Firebase Setup Guide - HumbleHackers Team App

## 🔥 Overview

This application uses Firebase for storing personal details. This guide walks you through setting up Firebase for the first time.

---

## 📋 Prerequisites

- A Google account (free or paid)
- Android Studio
- The HumbleHackers Team App project open in Android Studio

---

## ✅ Step 1: Create a Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Create a project"**
3. Enter project name: `HumbleHackers Team App` (or any name you prefer)
4. Click **"Continue"**
5. Choose your preferences (optional): Enable Google Analytics (not required)
6. Click **"Create project"** and wait for completion

---

## 📱 Step 2: Register Your Android App in Firebase

1. In the Firebase Console, click on your newly created project
2. Click on the **Android icon** to add an Android app
3. **Package name**: `com.example.sqlliteteamapp` (exact match with your gradle)
4. **App nickname**: `HumbleHackers Team App` (optional)
5. Click **"Register app"**

---

## 🔐 Step 3: Download Google Services JSON

1. After registering, Firebase will show you a download screen
2. Click **"Download google-services.json"**
3. **IMPORTANT**: Move the downloaded `google-services.json` file to:

```
app/google-services.json
```

**Exact path**: The file should be in the `app/` folder of your project, NOT in `app/src/` or elsewhere.

---

## 🏗️ Step 4: Enable Firestore in Firebase

1. In Firebase Console, go to **"Firestore Database"** (left menu)
2. Click **"Create database"**
3. Select region: `asia-south1` (or closest to you)
4. Click **"Create"**
5. **Start in test mode** (for development and testing)
   - Click **"Next"**
   - Click **"Enable"**

⚠️ **Production Note**: Test mode allows anyone to read/write. For production, set up proper security rules.

---

## 🔒 Step 5: Configure Firestore Security Rules (Optional but Recommended)

In Firebase Console > Firestore > Rules:

```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can only access their own data
    match /users/{userId}/{document=**} {
      allow read, write: if request.auth.uid == userId;
    }
  }
}
```

---

## 🔑 Step 6: Verify Project in Android Studio

1. After placing `google-services.json` in `app/` folder
2. Sync Gradle: **File** → **Sync Now**
3. Build the project: **Build** → **Make Project**
4. Check for any errors in the **Build** console

---

## 🛠️ How It Works in the App

### Authentication (Automatic)

The app uses **Anonymous Authentication** by default:

```kotlin
firebaseHelper = FirebaseHelper()
if (!firebaseHelper.isUserAuthenticated()) {
    // For production, implement proper Firebase Auth
}
```

### Data Structure in Firestore

Your personal details are stored like this:

```
users/
  └── {userId}/
      └── personal_details/
          └── profile {
              "name": "John Doe",
              "email": "john@example.com",
              "phone": "+91 98765 43210",
              "profession": "Developer",
              "city": "Bangalore",
              "bio": "Passionate coder",
              "createdAt": 1704067200000,
              "updatedAt": 1704067200000
            }
```

---

## 💾 Testing the Integration

1. Run the app on an emulator or device
2. Go to **Personal Details** section
3. Fill in your information
4. Click **"Save to Firebase"**
5. In Firebase Console > Firestore, you should see your data appear

---

## 🚨 Common Issues & Fixes

### Issue: `Failed to initialize Google Services`

**Solution**: 
- Make sure `google-services.json` is in the correct location: `app/google-services.json`
- Sync Gradle again: **File** → **Sync Now**

### Issue: `Firestore permission denied`

**Solution**:
- Make sure you set Firestore to **Test Mode** (readable/writable)
- Or configure proper security rules as shown above

### Issue: `Cannot find symbol 'FirebaseFirestore'`

**Solution**:
- Make sure Firebase dependencies are in `app/build.gradle.kts`
- Run **Sync Now** and rebuild

### Issue: Data not saving despite no error

**Solution**:
- Check Firestore Rules in Firebase Console
- Check internet connectivity
- Verify anonymous auth is working

---

## 🔄 Advanced: Email/Password Authentication

To switch from anonymous to email/password auth:

```kotlin
// In FirebaseHelper.kt
fun signInWithEmail(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
}

fun signUpWithEmail(email: String, password: String) {
    auth.createUserWithEmailAndPassword(email, password)
}
```

---

## 📚 Additional Resources

- [Firebase Android Setup](https://firebase.google.com/docs/android/setup)
- [Firestore Documentation](https://firebase.google.com/docs/firestore)
- [Firebase Security Rules](https://firebase.google.com/docs/firestore/security/start)

---

## ⭐ Key Points to Remember

✅ Always place `google-services.json` in `app/` folder
✅ Sync Gradle after adding the JSON file
✅ Test mode is for development only
✅ All personal details are encrypted in transit
✅ Each user's data is isolated and private

---

## 🎉 Next Steps

1. Complete the Firebase setup
2. Run the app
3. Go to Personal Details → Enter your info → Save to Firebase
4. Check Firebase Console to see your data

Enjoy using the HumbleHackers Team App! 🚀
