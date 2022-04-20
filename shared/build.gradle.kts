plugins {
    id("java-library")
    kotlin("multiplatform")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvm()
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

        val commonTest by getting {
            dependencies {
                implementation(Deps.commonTest)
                implementation(Deps.commonAnnotations)
            }
        }
    }
}