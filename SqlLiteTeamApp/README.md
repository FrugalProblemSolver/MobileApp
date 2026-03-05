# 🚀 HumbleHackers Team Application

A complete Android application demonstrating modern app architecture with team management, personal preferences, and cloud data storage.

---

## ✨ Features

### 1️⃣ **Team Members Management**
- View all team members details
- See individual member profiles with contact information
- Browse team member bios and specialties
- **Team Members**:
  - Soundar Arunachalam R M (AIR 1 GATE 2026)
  - Sethupathy R (Vibe Coder)
  - Suhas K S (Leetcode Guardian)

### 2️⃣ **Mindset Tracking (SharedPreferences)**
- Track your current mood/mindset with beautiful UI
- Options: Happy, Sad, Lonely, Excited, Calm, Focused, Confused, Grateful, Tired, Motivated
- View duration of current mindset
- Persistent storage using Android SharedPreferences
- Beautiful grid layout with emoji indicators

### 3️⃣ **Favorite Songs & Movies (SQLite/Room)**
- Add your favorite songs and movies
- Store with details: artist, genre, release year, rating
- SQLite database using Room for persistent local storage
- Search and filter favorites
- Rate your favorites (1-5 stars)
- Long-press to delete items

### 4️⃣ **Personal Details (Firebase Firestore)**
- Store personal information securely in Firebase
- Fields: Name, Email, Phone, Profession, City, Bio
- Save to Firestore with one click
- Load your saved profile anytime
- Real-time sync across devices
- Encrypted in-transit communication

---

## 🏗️ Architecture & Technology Stack

### Architecture Pattern
- **MVVM** (Model-View-ViewModel) ready
- Clean separation of concerns
- Repository pattern for data management

### Technologies Used

**Database**:
- 🗄️ **Room** (SQLite wrapper) - Local storage for favorites
- 🔥 **Firebase Firestore** - Cloud storage for personal details

**Storage**:
- 📝 **SharedPreferences** - Lightweight preference storage for mindset

**UI Components**:
- 📱 **RecyclerView** - Efficient list displays
- 🎨 **CardView** - Modern card-based layouts
- 🌈 **Material Design** - Beautiful, consistent UI
- 📊 **RatingBar** - Star-based ratings

**Background & Async**:
- ⚙️ **Kotlin Coroutines** - Asynchronous operations
- 🔄 **Flow** - Reactive data streaming

**Authentication**:
- 🔐 **Firebase Auth** - Ready for email/password or OAuth

---

## 📁 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/sqlliteteamapp/
│   │   │   ├── MainActivity.kt                 # Main entry point
│   │   │   ├── models/
│   │   │   │   ├── TeamMember.kt              # Team member data model
│   │   │   │   └── Favorite.kt                # SQLite entity for favorites
│   │   │   ├── database/
│   │   │   │   ├── TeamAppDatabase.kt         # Room database
│   │   │   │   └── FavoriteDao.kt             # Data access object
│   │   │   ├── repository/
│   │   │   │   └── FavoriteRepository.kt      # Repository pattern
│   │   │   ├── utils/
│   │   │   │   ├── MindsetPreferenceManager.kt # SharedPref helper
│   │   │   │   └── FirebaseHelper.kt          # Firebase helper
│   │   │   ├── ui/
│   │   │   │   ├── TeamMembersActivity.kt     # Team members list
│   │   │   │   ├── MemberDetailActivity.kt    # Member detail view
│   │   │   │   ├── MindsetActivity.kt         # Mood selector
│   │   │   │   ├── FavoritesActivity.kt       # Favorites list
│   │   │   │   └── PersonalDetailsActivity.kt # Profile manager
│   │   │   └── adapters/
│   │   │       ├── TeamMemberAdapter.kt       # Team list adapter
│   │   │       ├── MindsetAdapter.kt          # Mindset grid adapter
│   │   │       └── FavoriteAdapter.kt         # Favorites list adapter
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_team_members.xml
│   │   │   │   ├── activity_member_detail.xml
│   │   │   │   ├── activity_mindset.xml
│   │   │   │   ├── activity_favorites.xml
│   │   │   │   ├── activity_personal_details.xml
│   │   │   │   ├── item_team_member.xml
│   │   │   │   ├── item_mindset.xml
│   │   │   │   └── item_favorite.xml
│   │   │   ├── drawable/
│   │   │   │   ├── card_gradient_blue.xml
│   │   │   │   ├── card_gradient_green.xml
│   │   │   │   ├── card_gradient_orange.xml
│   │   │   │   ├── edit_text_background.xml
│   │   │   │   ├── ic_back.xml
│   │   │   │   └── ic_arrow_right.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   └── strings.xml
│   │   └── AndroidManifest.xml
│   └── test/ & androidTest/
├── build.gradle.kts                 # App-level dependencies
├── google-services.json             # Firebase config (to be added)
├── proguard-rules.pro               # ProGuard rules
└── README.md                        # This file
```

---

## 🔧 Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Android SDK 24 or higher
- Firebase account (Google account)

### Step 1: Clone/Open Project

```bash
# If cloning
git clone <repository-url>
cd SqlLiteTeamApp

# Or open in Android Studio
```

### Step 2: Add Google Services JSON

1. Create Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
2. Register Android app with package: `com.example.sqlliteteamapp`
3. Download `google-services.json`
4. Place in: `app/google-services.json`

📖 See [FIREBASE_SETUP_GUIDE.md](FIREBASE_SETUP_GUIDE.md) for detailed Firebase setup

### Step 3: Sync and Build

```bash
# In Android Studio
File → Sync Now
```

### Step 4: Run

```bash
# On emulator or connected device
Shift + F10  # Or click Run button
```

---

## 📚 Feature Details

### 1. Team Members
- **Location**: `MainActivity` → Team Members Card
- **Storage**: In-memory (application startup)
- **Database**: None required (static data)

```kotlin
// Access team member data
val members = TeamMembersData.members
val member = TeamMembersData.getMemberById(1)
```

### 2. Mindset Tracking (SharedPreferences)

**Storage**: Device local storage via SharedPreferences

```kotlin
val mindsetManager = MindsetPreferenceManager(context)

// Set mindset
mindsetManager.setMindset(MindsetPreferenceManager.Mindset.HAPPY)

// Get current mindset
val current = mindsetManager.getCurrentMindset()  // Happy (😊)

// Get duration mindset is active
val minutes = mindsetManager.getMindsetDuration()

// Format for display
val info = mindsetManager.getMindsetInfo()  // "😊 Happy for 30 minutes"
```

**Data Stored**: `SharedPreferences` key-value pairs
- `current_mindset`: Enum name (e.g., "HAPPY")
- `mindset_timestamp`: Long timestamp

### 3. Favorites (Room/SQLite)

**Storage**: SQLite database via Room

```kotlin
// Initialize
val database = TeamAppDatabase.getDatabase(context)
val repository = FavoriteRepository(database.favoriteDao())

// Add favorite
repository.addSong("Song Name", "Artist", "Genre", 4.5f)
repository.addMovie("Movie Name", "Genre", 2024, 4.5f)

// Get all favorites
repository.getAllFavorites().collect { favorites ->
    // Update UI
}

// Delete
repository.deleteFavorite(favorite)
```

**Database Schema**:
```sql
CREATE TABLE favorites (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT,              -- "song" or "movie"
    title TEXT,
    artist TEXT,           -- For songs
    genre TEXT,
    releaseYear INTEGER,   -- For movies
    rating REAL,
    description TEXT,
    dateAdded LONG
);
```

### 4. Personal Details (Firebase Firestore)

**Storage**: Cloud storage via Firebase Firestore

```kotlin
val firebaseHelper = FirebaseHelper()

// Save personal details
val details = PersonalDetail(
    name = "John Doe",
    email = "john@example.com",
    phone = "+91 98765 43210",
    profession = "Developer",
    city = "Bangalore",
    bio = "Passionate coder"
)

firebaseHelper.savePersonalDetails(details).onSuccess { message ->
    // Success: message = "Personal details saved successfully"
}.onFailure { error ->
    // Handle error
}

// Load personal details
firebaseHelper.getPersonalDetails().onSuccess { details ->
    // details: PersonalDetail? (null if not saved)
}.onFailure { error ->
    // Handle error
}
```

**Firestore Collection Structure**:
```
users/
  └── {userId}/
      └── personal_details/
          └── profile {
              name: String
              email: String
              phone: String
              profession: String
              city: String
              bio: String
              createdAt: Long
              updatedAt: Long
            }
```

---

## 🔐 Permissions & Privacy

### Required Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Data Privacy

- **Local Data** (SharedPreferences, SQLite): Stored securely on device
- **Cloud Data** (Firebase): Encrypted in transit & at rest
- **User Isolation**: Each user can only access their own Firestore data (via security rules)

---

## 🚀 Production Deployment

Before deploying to production:

1. **Firebase Security Rules**
   - Update Firestore rules (not just test mode)
   - Implement proper authentication

2. **Enable Proguard**
   ```gradle
   buildTypes {
       release {
           isMinifyEnabled = true
           proguardFiles(...)
       }
   }
   ```

3. **Update App Credentials**
   - Generate signed APK with release key

4. **Test Thoroughly**
   - Offline scenarios
   - Error handling
   - Data sync edge cases

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| `google-services.json` not found | Place file in `app/` folder, not elsewhere |
| Firestore permission denied | Set database to test mode or configure rules |
| Room compilation errors | Run `Sync Now` and invalidate caches |
| Coroutines not working | Add `kotlinx-coroutines-android` dependency |

---

## 📖 Dependencies

See `app/build.gradle.kts` for all dependencies:
- Room 2.6.1
- Firebase BOM 32.7.0
- Coroutines 1.7.3
- Material 1.12.0

---

## 📞 Team Members

👤 **Soundar Arunachalam R M**  
   AIR 1 GATE 2026 • BOS of IT 2027

👤 **Sethupathy R**  
   Vibe Coder • Professional Procrastinator

👤 **Suhas K S**  
   Leetcode Guardian • Upcoming Amazon Employee

---

## 📝 License

This project is for educational purposes.

---

## ✅ Checklist for Running

- [ ] Android Studio installed
- [ ] Project opened in Android Studio
- [ ] `google-services.json` placed in `app/` folder
- [ ] Gradle synced successfully
- [ ] Project builds without errors
- [ ] Run on emulator or device
- [ ] Test Personal Details → Save to Firebase
- [ ] Check Firebase Console to see saved data

---

## 🎉 You're All Set!

Enjoy using the HumbleHackers Team Application!

For Firebase questions, see [FIREBASE_SETUP_GUIDE.md](FIREBASE_SETUP_GUIDE.md)

Questions? Check the inline code comments throughout the project.

---

**Last Updated**: March 2026  
**Version**: 1.0
