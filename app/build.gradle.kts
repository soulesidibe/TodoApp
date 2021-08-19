plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply {
    from(file("$rootDir/gradle_tests_report.gradle.kts"))
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.soulesidibe.todoapp"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
//        kotlinCompilerVersion = "1.4.32"
//        kotlinCompilerVersion = "1.5.10"
        kotlinCompilerExtensionVersion = "1.0.0"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":device"))
    val koin_version = "3.1.2"
    val compose_version = "1.0.0"

    // Koin core features
    implementation("io.insert-koin:koin-core:$koin_version")
    // Koin test features
    testImplementation("io.insert-koin:koin-test:$koin_version")
    // Koin main features for Android (Scope,ViewModel ...)
    implementation("io.insert-koin:koin-android:$koin_version")

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")
    implementation("androidx.compose.material:material:${compose_version}")
    implementation("androidx.compose.ui:ui:${compose_version}")
    implementation("androidx.compose.ui:ui-tooling:${compose_version}")
    implementation("androidx.compose.compiler:compiler:${compose_version}")
    implementation("androidx.compose.material:material-icons-core:${compose_version}")
    implementation("androidx.compose.material:material-icons-extended:${compose_version}")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.16.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha03")
    implementation("androidx.activity:activity-compose:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${compose_version}")
}