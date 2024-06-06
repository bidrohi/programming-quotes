plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.google.ksp).apply(false)
    alias(libs.plugins.jetbrains.kotlin.android).apply(false)
    alias(libs.plugins.jetbrains.kotlin.multiplatform).apply(false)
    alias(libs.plugins.jetbrains.kotlin.compose).apply(false)
    alias(libs.plugins.jetbrains.kotlin.parcelize).apply(false)
    alias(libs.plugins.jetbrains.kotlin.serialization).apply(false)
    alias(libs.plugins.jetbrains.kotlinx.kover).apply(false)
    alias(libs.plugins.ktorfit).apply(false)
}

allprojects {
    layout.buildDirectory = File("${rootDir}/build/${projectDir.relativeTo(rootDir)}")
}
