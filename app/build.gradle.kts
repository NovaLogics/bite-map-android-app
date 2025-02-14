plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "novalogics.android.bitemap"
    compileSdk = 34

    defaultConfig {
        applicationId = "novalogics.android.bitemap"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true

        manifestPlaceholders["PLACES_API_KEY"] = project.properties["PLACES_API_KEY"] as String
        buildConfigField("String", "PLACES_API_KEY", "\"${project.properties["PLACES_API_KEY"]}\"")
    }

    buildTypes {
        val baseUrl = "\"api\""
        release {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = baseUrl
            )
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.android.libraries.places:places:3.5.0")
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.2.0")
    implementation("com.google.maps.android:android-maps-utils:3.6.0")
    implementation("com.google.maps.android:maps-compose:3.1.0")
    // AndroidX Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.paging)

    // UI & Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.navigate.compose)
    implementation(libs.androidx.hilt.compose)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room Database
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Google Material & Maps
    implementation(libs.material)
    implementation(libs.gsm.maps)
    implementation(libs.gsm.location)

    // Networking (Retrofit + OkHttp)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Utility Libraries
    implementation(libs.coil)
    implementation(libs.leakcanary)
    implementation(libs.timber)

    // Pluto Debugging Tools
    implementation(libs.pluto)
    implementation(libs.pluto.network)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
}

