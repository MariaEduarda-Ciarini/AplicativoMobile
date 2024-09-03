plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "br.com.fiap.climaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.fiap.climaapp"
        minSdk = 28
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")

    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.navigation:navigation-fragment:2.7.7")

    implementation("io.insert-koin:koin-android:3.4.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    implementation("io.coil-kt:coil:2.5.0")

    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("com.google.firebase:firebase-bom:33.2.0")

}