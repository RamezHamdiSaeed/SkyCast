plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.skycast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.skycast"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding =true
        dataBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.test.ext:junit-ktx:1.1.5") // Moved to the implementation block
    testImplementation("junit:junit:4.13.2") // Removed duplicate
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //google play services
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    //picasso
    implementation ("com.squareup.picasso:picasso:2.8")
    //for Kotlin + workManager
    implementation ("androidx.work:work-runtime-ktx:2.9.0")
    //retrofit with gson
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Coroutines Dependencies
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    //Room
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    //picasso
    implementation ("com.squareup.picasso:picasso:2.8")
    //mvvm
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //default testing dependencies
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Removed duplicate
    // assertion framework
    testImplementation ("org.hamcrest:hamcrest:2.2")
    testImplementation ("org.hamcrest:hamcrest-library:2.2")
    //AndroidX test dependencies
    testImplementation ("androidx.test:core-ktx:1.5.0") // Updated version
    //Robolectric
    testImplementation ("org.robolectric:robolectric:4.8")
    //InstantTaskExecutorRule
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    implementation ("androidx.activity:activity-ktx:1.8.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation ("androidx.fragment:fragment-ktx:1.6.2")

}