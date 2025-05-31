import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.native.coroutines)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.test.mokkery)
    alias(libs.plugins.jetbrains.kotlinx.kover)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "QuotesShared"
            isStatic = true
        }
    }

    jvm("desktop")

    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "quotesApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "quotesApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
            testTask {
                useKarma {
                    // useFirefox()
                    // useChrome()
                    // useSafari()
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.jetbrains.kotlinx.coroutines.core)
            implementation(libs.jetbrains.lifecycle)

            implementation(libs.kotlin.inject.runtime)

            implementation(libs.kermit)

            implementation(libs.jetbrains.kotlinx.json)

            implementation(libs.ktor.core)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)

            implementation(libs.ktorfit.lib)

            implementation(libs.bhandar)
        }
        commonTest.dependencies {
            implementation(libs.test.junit)
            implementation(libs.test.jetbrains.kotlin)
            implementation(libs.test.jetbrains.kotlinx.coroutines)
            implementation(libs.test.ktor.mock)
            implementation(libs.test.turbine)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.jetbrains.kotlinx.coroutines.android)

            implementation(libs.ktor.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.jetbrains.kotlinx.coroutines.swing)

            implementation(libs.ktor.cio)
        }
        macosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.js)
        }
    }
}

dependencies {
    with(libs.kotlin.inject.compiler) {
        add("kspCommonMainMetadata", this)
    }
}

android {
    namespace = "dev.quotes"
    compileSdk = libs.versions.build.sdk.compile.get().toInt()
    buildToolsVersion = libs.versions.build.tools.get()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "dev.quotes"
        minSdk = libs.versions.build.sdk.min.get().toInt()
        targetSdk = libs.versions.build.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    lint {
        warningsAsErrors = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        "coreLibraryDesugaring"(libs.jdk.desugar)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.quotes"
            packageVersion = "1.0.0"
        }
    }
}
