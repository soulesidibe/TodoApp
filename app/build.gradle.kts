plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply {
    from(file("$rootDir/gradle_tests_report.gradle.kts"))
}

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.soulesidibe.todoapp"
        minSdk = Versions.minSdk
        targetSdk = Versions.compileSdk
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":data"))
    implementation(project(":device"))

    implementation(Deps.koin_core)
    implementation(Deps.koin_android)
    implementation(Deps.androidx_core_ktx)
    implementation(Deps.androidx_app_compat)
    implementation(Deps.material)
    implementation(Deps.androidx_compose_constraint_layout)
    implementation(Deps.androidx_compose_navigation)
    implementation(Deps.androidx_compose_material)
    implementation(Deps.androidx_compose_ui)
    implementation(Deps.androidx_compose_tooling)
    implementation(Deps.androidx_compose_compiler)
    implementation(Deps.androidx_compose_material_icons_core)
    implementation(Deps.androidx_compose_material_icons_extended)
    implementation(Deps.accompanist_system_ui_controller)

    implementation(Deps.androidx_lifecycle_runtime)
    implementation(Deps.androidx_compose_activity)
    // Koin test features
    androidTestImplementation(Deps.androidx_junit)
    androidTestImplementation(Deps.androidx_espresso)
    androidTestImplementation(Deps.androidx__compose_junit)

    testImplementation(Deps.junit)
    testImplementation(Deps.coroutines_test)
    testImplementation(Deps.hamcrest)
    testImplementation(Deps.mockito)
    testImplementation(Deps.mockk)
    testImplementation(Deps.koin_test)
}