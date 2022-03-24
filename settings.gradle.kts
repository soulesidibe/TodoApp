pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "Todo App"
include(":app")
include(":domain")
include(":data")
include(":device")