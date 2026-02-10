# Quick Reference Guide - HumbleHackers App Features

## 🎯 Quick Navigation

### Feature 1: Hourly Notifications
**Files**: `StatusNotificationWorker.kt`, `NotificationHelper.kt`, `MainActivity.kt`
**How it starts**: `MainActivity.onCreate()` calls `scheduleHourlyNotifications()`
**Check**: Only shows 9 AM-5 PM, Monday-Friday
**Custom status**: Edit `NotificationHelper.getTeamMemberStatus()`

### Feature 2: Exit Dialog  
**File**: `MainActivity.kt`
**Method**: `onBackPressed()`
**When shown**: User presses back on MainActivity
**Actions**: Yes (cancels work + exit), No (dismiss)

### Feature 3: Progress Ring
**File**: `BirthdayActivity.kt` and `activity_birthday.xml`
**Element**: `ProgressBar` with id `progressBar`
**Show**: `progressBar.visibility = ProgressBar.VISIBLE`
**Hide**: `progressBar.visibility = ProgressBar.GONE`

### Feature 4: Birthday Manager
**Main File**: `BirthdayActivity.kt`
**Data Storage**: `BirthdayRepository.kt` (SharedPreferences)
**Data Model**: `BirthdayEvent.kt`
**Notification**: `BirthdayNotificationWorker.kt`
**UI Layout**: `activity_birthday.xml`

---

## 📝 Code Snippets for Common Tasks

### Add a New Team Member
```kotlin
// In BirthdayActivity.kt
private val teamMembers = mapOf(
    0 to "Soundar Arunachalam R M",
    1 to "Sethupathy R",
    2 to "Suhas K S",
    3 to "New Member Name"  // ADD HERE
)
```

### Change Notification Hour
```kotlin
// In StatusNotificationWorker.kt
val isWorkingHour = currentHour in 8..18  // 8 AM to 6 PM
```

### Add Custom Status Message
```kotlin
// In NotificationHelper.kt
fun getTeamMemberStatus(): String {
    val statuses = listOf(
        "Your custom status here 🎯"  // ADD HERE
    )
    return statuses.random()
}
```

### Get All Birthdays
```kotlin
val repository = BirthdayRepository(context)
val allBirthdays = repository.getAllBirthdays()
```

### Save a Birthday
```kotlin
val birthday = BirthdayEvent(
    id = System.currentTimeMillis().toInt(),
    memberId = 1,
    memberName = "Soundar Arunachalam R M",
    date = LocalDate.of(2025, 3, 15),
    time = LocalTime.of(9, 0),
    greetingMessage = "Happy Birthday!"
)
repository.addBirthday(birthday)
```

### Delete a Birthday
```kotlin
repository.deleteBirthday(birthdayId)
```

### Cancel All Notifications
```kotlin
WorkManager.getInstance(context).cancelAllWork()
```

---

## 🔍 Key Classes & Their Purpose

| Class | Purpose | Location |
|-------|---------|----------|
| `StatusNotificationWorker` | Sends hourly status notifications | workers/ |
| `BirthdayNotificationWorker` | Sends birthday notifications | workers/ |
| `NotificationHelper` | Creates & manages notifications | utils/ |
| `ScheduleManager` | Manages work scheduling | utils/ |
| `BirthdayEvent` | Data model for birthdays | models/ |
| `BirthdayRepository` | Handles birthday storage | repositories/ |
| `BirthdayActivity` | UI for birthday management | root/ |
| `MainActivity` | Main app screen & exit dialog | root/ |

---

## 🧪 Common Tests

### Test Hourly Notifications
1. Check time between 9 AM - 5 PM
2. Verify it's a weekday
3. Wait for next hour or manually trigger task scheduler

### Test Exit Dialog
1. Open MainActivity
2. Press back button
3. Verify dialog appears
4. Click Yes to exit / No to stay

### Test Progress Ring
1. Open BirthdayActivity
2. Try saving a birthday
3. Progress ring should appear briefly

### Test Birthday Notification
1. Set birthday for today
2. Set time to current time or 1 minute from now
3. Keep app open
4. Wait for notification

---

## 📱 UI Components Reference

| Component | File | ID |
|-----------|------|-----|
| Birthday Member Spinner | activity_birthday.xml | memberSpinner |
| Select Date Button | activity_birthday.xml | addDateButton |
| Select Time Button | activity_birthday.xml | addTimeButton |
| Save Birthday Button | activity_birthday.xml | saveBirthdayButton |
| Progress Bar | activity_birthday.xml | progressBar |
| Birthday List | activity_birthday.xml | birthdayListView |
| Delete All Button | activity_birthday.xml | deleteAllButton |

---

## 🛠️ Debugging Tips

### App crashes on startup?
```bash
# Check build errors
./gradlew clean build

# Check logcat for errors
adb logcat | grep "ERROR\|CRASH"
```

### Notifications not appearing?
1. Check `NotificationHelper.createNotificationChannels()` is called
2. Verify manifest has `POST_NOTIFICATIONS` permission
3. Check notification channel is created for correct API level

### Birthday not triggering?
1. Verify `WorkManager` is enabled
2. Check date/time calculation in `scheduleBirthdayNotification()`
3. Ensure app has permission: `SCHEDULE_EXACT_ALARM`

### UI elements not showing?
1. Check layout XML for typos
2. Verify IDs match in code
3. Rebuild and clean cache: `./gradlew clean build`

---

## 📚 Important Methods

### MainActivity
```kotlin
onCreate() → Initialize notifications, schedule hourly notifications
onCreateOptionsMenu() → Inflate menu
onOptionsItemSelected() → Handle menu clicks
onBackPressed() → Show exit dialog
showPopupMenu() → Display popup menu
```

### BirthdayActivity
```kotlin
onCreate() → Initialize UI and load birthdays
showDatePicker() → Open DatePickerDialog
showTimePicker() → Open TimePickerDialog
saveBirthday() → Save and schedule notification
loadBirthdays() → Fetch and display birthdays
deleteBirthday() → Remove birthday record
scheduleBirthdayNotification() → Schedule WorkManager task
```

### BirthdayRepository
```kotlin
addBirthday() → Save new birthday
getAllBirthdays() → Get all saved birthdays
getBirthdayById() → Get single birthday
deleteBirthday() → Remove birthday
updateBirthday() → Update existing birthday
clearAllBirthdays() → Delete all birthdays
```

---

## 🎯 Feature Checklist

- [ ] Hourly notifications show during work hours
- [ ] Exit dialog appears when pressing back
- [ ] Progress ring shows during operations
- [ ] Can add birthdays with date/time picker
- [ ] Birthdays save to SharedPreferences
- [ ] Can view saved birthdays
- [ ] Can delete individual birthdays
- [ ] Can delete all birthdays
- [ ] Birthday notifications trigger on date
- [ ] Menu items work in all menus

---

## 🚀 Performance Tips

1. **Batch Birthday Operations**: Load all at once, don't reload frequently
2. **Cancel Work on Exit**: Always call `WorkManager.cancelAllWork()` when appropriate
3. **Minimize Progress Indicators**: Show only for long operations
4. **Cache Team Members**: Store in a constant, don't recreate
5. **Use SharedPreferences**: Fast for small data like birthdays

---

## 📞 Emergency Fixes

### If app won't compile:
```bash
./gradlew clean
./gradlew build
```

### If notifications won't work:
1. Check Android version is 8+ (API 26+)
2. Verify `POST_NOTIFICATIONS` permission in manifest
3. Check `createNotificationChannels()` is called

### If birthdays aren't saved:
1. Check `BirthdayRepository` methods are being called
2. Verify SharedPreferences file is not being cleared
3. Test with `android:debuggable="true"` in manifest

### If menu items don't work:
1. Rebuild app completely
2. Clear app data and reinstall
3. Check menu XML syntax is correct

---

## 📖 Related Documentation

- Full implementation guide: `IMPLEMENTATION_GUIDE.md`
- Architecture plan: `IMPLEMENTATION_PLAN.md`
- Android WorkManager docs: https://developer.android.com/topic/libraries/architecture/workmanager
- DatePicker/TimePicker: https://developer.android.com/guide/topics/ui/controls/pickers

---

**Last Updated**: February 10, 2026  
**App Version**: 1.0  
**Min SDK**: 24  
**Target SDK**: 34
