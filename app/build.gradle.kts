import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val secretPropertiesFile: File? = rootProject.file("./secret.properties")
val secretProperties = Properties()
secretProperties.load(FileInputStream(secretPropertiesFile))

android {
    namespace = "com.mapxus.uisdkshowcase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mapxus.uisdkshowcase"
        minSdk = 26
        targetSdk = 36
        versionCode = 4
        versionName = "1.3.0"

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
    flavorDimensions += "sdk"
    productFlavors {
        create("mapxus") {
            dimension = "sdk"
            buildConfigField("String", "appid", "\"${secretProperties["appid-prod"] as String}\"")
            buildConfigField("String", "secret", "\"${secretProperties["secret-prod"] as String}\"")
        }
        create("jp") {
            dimension = "sdk"
            applicationId = "com.mapxus.uisdkshowcase.jp"
            buildConfigField("String", "appid", "\"${secretProperties["appid-jp"] as String}\"")
            buildConfigField("String", "secret", "\"${secretProperties["secret-jp"] as String}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)

    // Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    "mapxusImplementation"(libs.mapxus.dropin)
    "jpImplementation"(libs.mapxus.dropin.jp)

    implementation(libs.compose.colorpicker)
}