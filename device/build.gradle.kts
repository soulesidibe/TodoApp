plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}
apply {
    from(file("$rootDir/gradle_tests_report.gradle.kts"))
}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = Versions.compileSdk
    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.compileSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    android()
    ios {
        binaries.framework {
            baseName = "Shared-Device"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":data"))
                implementation(Deps.kotlin)
                implementation(Deps.coroutines_core)
                implementation(Deps.koin_core)
                implementation(Deps.sqldelight_coroutines)
            }
        }

        val iosX64Main by getting {
            dependencies {
                implementation(Deps.koin_iosx64)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation(Deps.koin_iosarm64)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Deps.sqldelight_android)
                implementation(Deps.koin_android)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Deps.sqldelight_native)

            }
        }

    }
}

sqldelight {
    database("Database") { // This will be the name of the generated database class.
        packageName = "com.soulesidibe.device"
    }
}