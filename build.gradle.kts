// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidGradlePlugin) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.hiltGradlePlugin) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}
true