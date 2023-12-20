// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    // kotlin 버전 1.9.0 -> 1.7.20
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    // hilt
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
}