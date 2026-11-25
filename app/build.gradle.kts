// app/build.gradle.kts — приложение-каталог плагинов OpenChamber (Android WebView)
// Сборка: ./gradlew assembleRelease (см. build-local.sh на aarch64)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.opencode.plugins"
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file("../openchamber-plugins-release.keystore")
            storePassword = "openchamberplugins"
            keyAlias = "openchamberplugins"
            keyPassword = "openchamberplugins"
        }
    }

    defaultConfig {
        applicationId = "ru.opencode.plugins"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
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
    kotlinOptions { jvmTarget = "17" }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.webkit:webkit:1.12.1")
}
