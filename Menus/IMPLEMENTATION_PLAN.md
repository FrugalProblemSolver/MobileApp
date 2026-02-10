# HumbleHackers Team App - Feature Implementation Plan

## 📌 Overview
Adding four critical features to enhance the team application:
1. ✅ Hourly status notifications (with timetable)
2. ✅ Alert dialog for app exit
3. ✅ Progress ring/dialog for loading states
4. ✅ Birthday date/time picker with scheduled notifications

---

## 🏛️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                       MainActivity                           │
│  - Options Menu                                              │
│  - Popup Menu                                                │
│  - Exit Alert Dialog                                         │
├─────────────────────────────────────────────────────────────┤
│          WorkManager (Background Task Scheduler)              │
├────────────────────┬──────────────────┬──────────────────────┤
│   HourlyWorker     │  BirthdayWorker  │  NotificationManager │
│   (Every Hour)     │  (On Birthday)   │  (Creates Notifications)
└────────────────────┴──────────────────┴──────────────────────┘
         ↓                    ↓
┌─────────────────────────────────────────────────────────────┐
│           Birthday Management Activity                       │
│  - DatePicker                                                │
│  - TimePicker                                                │
│  - Birthday List                                             │
│  - Progress Ring (Save/Load)                                 │
└─────────────────────────────────────────────────────────────┘
         ↓ Storage
┌─────────────────────────────────────────────────────────────┐
│   SharedPreferences / Room Database (Birthday Data)          │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 Dependencies to Add

### In `app/build.gradle.kts`:
```gradle
// WorkManager for background task scheduling
implementation("androidx.work:work-runtime-ktx:2.8.1")

// Material Design 3 (ProgressIndicator, AlertDialog, DatePicker, TimePicker)
implementation("com.google.android.material:material:1.12.0") // Already included

// Room Database (optional, for persistent birthday storage)
implementation("androidx.room:room-runtime:2.6.0")
kapt("androidx.room:room-compiler:2.6.0")

// Preference DataStore (for timetable storage)
implementation("androidx.datastore:datastore-preferences:1.0.0")
```

---

## 🔄 Feature-by-Feature Implementation

### **Feature 1: Hourly Status Notifications**

#### Files to Create:
1. `StatusNotificationWorker.kt` - Background task
2. `NotificationHelper.kt` - Notification builder
3. `ScheduleManager.kt` - Manages work scheduling

#### Flow:
```
WorkManager (Periodic Task)
    ↓
StatusNotificationWorker.doWork()
    ↓
Check Timetable (9 AM - 5 PM)
    ↓
Get Team Member Status
    ↓
Show Notification
    ↓
Log/Store Last Notification Time
```

#### Key Code:
```kotlin
// In MainActivity.onCreate():
val statusWork = PeriodicWorkRequestBuilder<StatusNotificationWorker>(
    1, TimeUnit.HOURS
).build()

WorkManager.getInstance(this).enqueueUniquePeriodicWork(
    "hourly_status",
    ExistingPeriodicWorkPolicy.KEEP,
    statusWork
)
```

---

### **Feature 2: Alert Dialog for App Exit**

#### Files to Modify:
1. `MainActivity.kt` - Override onBackPressed()

#### Flow:
```
User clicks BACK
    ↓
onBackPressed() triggered
    ↓
Show AlertDialog (Confirm Exit)
    ↓
If YES → Cancel notifications → finish()
If NO → Dismiss dialog
```

#### Key Code:
```kotlin
override fun onBackPressed() {
    AlertDialog.Builder(this)
        .setTitle("Exit Application")
        .setMessage("Are you sure you want to exit?")
        .setPositiveButton("Yes") { _, _ ->
            // Cancel all notifications
            WorkManager.getInstance(this).cancelAllWork()
            finish()
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}
```

---

### **Feature 3: Progress Ring/Dialog**

#### Files to Create:
1. `ProgressDialogHelper.kt` - Utility for progress dialogs
2. Update `BirthdayActivity.xml` - Add ProgressIndicator

#### Flow:
```
Load Birthday Data
    ↓
Show Progress Ring
    ↓
Fetch/Process Data
    ↓
Hide Progress Ring
    ↓
Display Birthdays
```

#### Layout Integration:
```xml
<ProgressIndicator
    android:id="@+id/progressRing"
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:indeterminate="true"
    style="@style/Widget.MaterialComponents.ProgressIndicator.Circular" />
```

---

### **Feature 4: Birthday Date/Time Picker with Notifications**

#### Files to Create:
1. `BirthdayActivity.kt` - Main activity for birthday management
2. `BirthdayEntity.kt` - Data model
3. `BirthdayRepository.kt` - Birthday data handling
4. `BirthdayWorker.kt` - Scheduled birthday notifications
5. `activity_birthday.xml` - UI layout
6. `BirthdayAdapter.kt` - RecyclerView adapter (optional)

#### Flow:
```
BirthdayActivity Opens
    ↓
Load Existing Birthdays (with Progress Ring)
    ↓
User Clicks "Add Birthday"
    ↓
Show DatePicker → Click Date
    ↓
Show TimePicker → Click Time
    ↓
Save Birthday + Schedule Work
    ↓
On Birthday Date + Time
    ↓
BirthdayWorker Fires
    ↓
Show Birthday Notification
```

#### Data Model:
```kotlin
data class BirthdayEvent(
    val memberId: Int,          // R.id.member1, member2, member3
    val memberName: String,     // "Soundar Arunachalam R M"
    val date: LocalDate,        // Birthday date (Month, Day)
    val time: LocalTime,        // Notification time (e.g., 9:00 AM)
    val greetingMessage: String // Custom greeting
)
```

---

## 📱 UI Components Required

### 1. **Update AndroidManifest.xml**
```xml
<activity android:name="com.example.menus.BirthdayActivity"
    android:exported="true" />
```

### 2. **New Layouts**
- `activity_birthday.xml` - Main birthday management screen
- `dialog_add_birthday.xml` - Add birthday dialog (optional)
- `item_birthday.xml` - Birthday list item (if using RecyclerView)

### 3. **New Menu Items**
- Add "Birthdays" to options menu
- Add "Birthdays" to popup menu

---

## 🔐 Required Permissions

### In `AndroidManifest.xml`:
```xml
<!-- For notifications -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- For background work -->
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.INTERNET" />
```

---

## ⚙️ Notification Schedule Management

### Timetable Structure:
```kotlin
data class NotificationSchedule(
    val startHour: Int = 9,      // 9 AM
    val endHour: Int = 17,       // 5 PM
    val daysOfWeek: List<Int>,   // 1-7 (Sunday-Saturday)
    val enabled: Boolean = true
)
```

### Working Hours Check:
```kotlin
private fun isWithinWorkingHours(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return currentHour in 9..17 // 9 AM to 5 PM
}
```

---

## 🗂️ File Structure After Implementation

```
app/src/main/java/com/example/menus/
├── MainActivity.kt (Modified)
├── AboutUsActivity.kt
├── TeamMembersActivity.kt
├── TeamDetailsActivity.kt
├── ProjectDescriptionActivity.kt
├── MemberDescriptionActivity.kt
├── BirthdayActivity.kt (NEW)
├── workers/
│   ├── StatusNotificationWorker.kt (NEW)
│   └── BirthdayNotificationWorker.kt (NEW)
├── models/
│   ├── BirthdayEvent.kt (NEW)
│   └── NotificationSchedule.kt (NEW)
├── repositories/
│   └── BirthdayRepository.kt (NEW)
├── utils/
│   ├── NotificationHelper.kt (NEW)
│   ├── ProgressDialogHelper.kt (NEW)
│   └── ScheduleManager.kt (NEW)
└── adapters/
    └── BirthdayAdapter.kt (NEW - Optional)

app/src/main/res/layout/
├── activity_birthday.xml (NEW)
├── dialog_add_birthday.xml (NEW - Optional)
├── item_birthday.xml (NEW - Optional)

app/src/main/res/menu/
├── options_menu.xml (Modified - Add Birthdays)
└── popup_menu.xml (Modified - Add Birthdays)
```

---

## 🎯 Implementation Phases

### Phase 1: Setup (Day 1)
- ✅ Update dependencies
- ✅ Add permissions to manifest
- ✅ Update menu resources

### Phase 2: Core Infrastructure (Day 2)
- ✅ Create NotificationHelper
- ✅ Create ScheduleManager
- ✅ Add Exit Alert Dialog

### Phase 3: Working Tasks (Day 3)
- ✅ Create StatusNotificationWorker
- ✅ Implement hourly notifications
- ✅ Test timetable filtering

### Phase 4: Birthday Management (Day 4)
- ✅ Create Birthday data model
- ✅ Create BirthdayRepository
- ✅ Create BirthdayActivity with UI

### Phase 5: Advanced Features (Day 5)
- ✅ Create BirthdayNotificationWorker
- ✅ Implement DatePicker/TimePicker
- ✅ Schedule birthday notifications

### Phase 6: Polish & Testing (Day 6)
- ✅ Add Progress Ring to activities
- ✅ Complete error handling
- ✅ Full app testing

---

## 🧪 Testing Checklist

- [ ] Hourly notifications fire correctly
- [ ] Notifications only show during 9 AM - 5 PM
- [ ] Exit dialog appears and works
- [ ] Progress ring shows during data operations
- [ ] DatePicker & TimePicker work
- [ ] Birthdays save to storage
- [ ] Birthday notifications trigger on date/time
- [ ] Multiple birthdays can be added
- [ ] Birthday list displays correctly
- [ ] App doesn't crash on config changes

---

## 🚀 Next Steps

1. Review this plan
2. Run `./gradlew build` to verify dependencies
3. Start Phase 1 implementation
4. Test each feature before moving to next phase
