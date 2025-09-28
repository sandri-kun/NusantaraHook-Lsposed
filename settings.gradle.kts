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
        maven ("https://jitpack.io")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")

        maven(url = "https://api.xposed.info")
    }
}

rootProject.name = "NusantaraHook Lsposed"
include(":app")
