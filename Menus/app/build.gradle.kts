plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.menus"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.menus"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}


    dependencies {
        // Use 1.13.0 or 1.15.0 instead of 1.17.0
        implementation("androidx.core:core-ktx:1.15.0")

        // Use 1.9.0 instead of 1.12.3
        implementation("androidx.activity:activity:1.9.3")

        // Use 1.2.1 instead of 1.3.0
        implementation("androidx.appcompat:appcompat:1.7.0")

        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.constraintlayout:constraintlayout:2.2.0")

        // NEW: WorkManager for background task scheduling
        implementation("androidx.work:work-runtime-ktx:2.8.1")

        // NEW: DataStore for timetable preferences
        implementation("androidx.datastore:datastore-preferences:1.0.0")

        // NEW: Room Database for birthday storage
        implementation("androidx.room:room-runtime:2.6.0")
        implementation("androidx.room:room-ktx:2.6.0")
        annotationProcessor("androidx.room:room-compiler:2.6.0")

        // Testing
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
