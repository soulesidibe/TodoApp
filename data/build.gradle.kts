plugins {
    id("java-library")
    kotlin("multiplatform")
}

apply {
    from(file("$rootDir/gradle_tests_report.gradle.kts"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvm()
    ios {
        binaries.framework {
            baseName = "Shared-Data"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(Deps.kotlin)
                implementation(Deps.coroutines_core)
                implementation(Deps.koin_core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(Deps.commonTest)
                implementation(Deps.commonAnnotations)
            }
        }
    }

}

//dependencies {
//    implementation(project(":shared"))
//    implementation(Deps.kotlin)
//    implementation(Deps.coroutines_android)
//    implementation(Deps.koin_core)
//
//    testImplementation(Deps.junit)
//    testImplementation(Deps.coroutines_test)
//    testImplementation(Deps.hamcrest)
//    testImplementation(Deps.mockito)
//    testImplementation(Deps.mockk)
//    testImplementation(Deps.koin_test)
//}