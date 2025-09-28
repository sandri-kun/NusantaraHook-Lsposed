plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "id.nusantarahook.lsposed"
    compileSdk = 36

    defaultConfig {
        applicationId = "id.nusantarahook.lsposed"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        signingConfigs {
            create("releaseConfig") {
                keyAlias = "nusantara_key"
                keyPassword = "Nusantara123!"
                storeFile = file("nusantara_release.jks")
                storePassword = "Nusantara123!"
            }

            create("debugConfig") {
                keyAlias = "nusantara_key"
                keyPassword = "Nusantara123!"
                storeFile = file("nusantara_release.jks")
                storePassword = "Nusantara123!"
            }
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debugConfig")
            isDebuggable = false
        }

        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("releaseConfig")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Androidx
    implementation(libs.constraintlayout.compose)

    // Core
    //coreLibraryDesugaring(libs.desugar)
    //implementation(libs.bundles.voyager)
    implementation(platform(libs.coil.bom))
    implementation(libs.bundles.coil)
    implementation(libs.landscapist.coil)
    implementation(libs.landscapist.placeholder)
    //implementation(libs.reorderable)
    //implementation(libs.swipe)
    //implementation(libs.grid)
    //implementation(libs.lazycolumnscrollbar)

    // XposedBridge
    implementation(libs.dexkit)
    compileOnly(libs.xposed)
}