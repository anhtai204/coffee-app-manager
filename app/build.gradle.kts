plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.coffeeappmanage"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.coffeeappmanage"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.code.gson:gson:2.11.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation ("com.sun.mail:android-activation:1.6.2")
    implementation ("com.sun.mail:android-mail:1.6.2")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.android.material:material:1.11.0")
}