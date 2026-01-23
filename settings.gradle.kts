import java.net.URI

val MAPXUS_RELEASE_URL: String by settings
val MAPXUS_RELEASE_USERNAME: String by settings
val MAPXUS_RELEASE_PASSWORD: String by settings
val MAPXUS_SNAPSHOT_URL: String by settings
val MAPXUS_SNAPSHOT_USERNAME: String by settings
val MAPXUS_SNAPSHOT_PASSWORD: String by settings
val MAPXUS_DROP_IN_UI_URL: String by settings

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
        maven("https://jitpack.io")
        maven {
            name = "Mapxus Release Repository"
            url = URI(MAPXUS_RELEASE_URL)
            isAllowInsecureProtocol = true
            credentials {
                username = MAPXUS_RELEASE_USERNAME
                password = MAPXUS_RELEASE_PASSWORD
            }
        }
        maven {
            name = "Mapxus Snapshot Repository"
            url = URI(MAPXUS_SNAPSHOT_URL)
            isAllowInsecureProtocol = true
            credentials {
                username = MAPXUS_SNAPSHOT_USERNAME
                password = MAPXUS_SNAPSHOT_PASSWORD
            }
        }
        maven {
            name = "dropin"
            url = URI(MAPXUS_DROP_IN_UI_URL)
            credentials {
                username = MAPXUS_RELEASE_USERNAME
                password = MAPXUS_RELEASE_PASSWORD
            }
        }
    }
}

rootProject.name = "UISDK Showcase"
include(":app")
