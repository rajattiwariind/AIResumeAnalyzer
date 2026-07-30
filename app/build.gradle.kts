plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.airesumeanalyzer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.airesumeanalyzer"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildFeatures {
            viewBinding = true
        }
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

        sourceCompatibility = JavaVersion.VERSION_17

        targetCompatibility = JavaVersion.VERSION_17

    }

    dependencies {

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)

        implementation(libs.lottie)

        implementation(libs.pdfbox)


        implementation(libs.retrofit)
        implementation(libs.converter.gson)

        implementation(libs.mpchart)

        implementation(libs.room.runtime)
        annotationProcessor(libs.room.compiler)
        implementation(libs.okhttp)
        testImplementation(libs.junit)

        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
}