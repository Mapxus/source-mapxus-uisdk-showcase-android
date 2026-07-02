import java.net.URI

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            name = "Mapxus Drop In UI Repository"
            url = URI("https://nexus3.mapxus.com/repository/dropin-ui-android")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "UISDK Showcase"
include(":app")
