pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "Todo App"
include(":app")
include(":data")
include(":device")
include(":shared")