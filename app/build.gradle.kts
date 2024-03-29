plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.synrgy.travelid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.synrgy.travelid"
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Optional -- Robolectric environment
    testImplementation("androidx.test:core:1.5.0")
    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:5.3.1")
    // Optional -- mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    // Optional -- Mockk framework
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Jetpack Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.makeramen:roundedimageview:2.3.0")

    // Kotlin Couroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    // Retrofit
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //koin
    implementation("io.insert-koin:koin-bom:3.5.1")
    implementation("io.insert-koin:koin-android")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("io.coil-kt:coil:2.5.0")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Work Manager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Chucker
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")

    // SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Rounded Image View
    implementation("com.makeramen:roundedimageview:2.3.0")

    // Avatar Image Generator
    implementation("com.github.amoskorir:avatarimagegenerator:1.5.0")

    // Chip Navigation Bar
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // Custom Switch
    implementation("com.github.codersrouteandroid:flexible-switch:1.0")

    // Image Picker
    implementation("com.github.dhaval2404:imagepicker:2.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-perf")

    // recyclerview
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    // cardview
    implementation ("androidx.cardview:cardview:1.0.0")

    // lottie
    implementation("com.airbnb.android:lottie:3.4.0")
}