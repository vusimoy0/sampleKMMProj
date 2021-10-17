import KotlinPlugins.android

plugins {
    id(Plugins.androidApplication)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    id(Plugins.hilt)
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {
    implementation(project(":shared"))

    implementation(Accompanist.coilkt)
    implementation(AndroidX.appCompat)
    implementation(Compose.runtime)
    implementation(Compose.runtimeLiveData)
    implementation(Compose.ui)
    implementation(Compose.uiTooling)
    implementation(Compose.foundation)
    implementation(Compose.compiler)
    implementation(Compose.constraintLayout)
    implementation(Compose.activity)
    implementation(Compose.navigation)

    implementation(Compose.material)

    implementation(Ktor.cio)
    implementation(Ktor.android)
    implementation(Ktor.okHttp)

    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigation)
    kapt(Hilt.hiltCompiler)
    //implementation(Hilt.hiltNavigationCompose)

    implementation(Google.material)
    implementation(Kotlinx.datetime)

    debugImplementation(SquareUp.leakCanary)
}

android {
    compileSdk = Application.compileSdk
    defaultConfig {
        applicationId = Application.appId
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}