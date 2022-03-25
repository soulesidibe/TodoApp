plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = "30.0.3"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.compileSdk
    }
}

kotlin {
    android()
    ios {
        binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.kotlin)
                implementation(Deps.coroutines_core)
                implementation(Deps.koin_core)
            }
        }

//        val commonTest by getting {
//            dependencies {
//                implementation(Deps.junit)
//                implementation(Deps.coroutines_test)
//                implementation(Deps.hamcrest)
//                implementation(Deps.mockito)
//                implementation(Deps.mockk)
//                implementation(Deps.koin_test)
//            }
//        }
    }
}