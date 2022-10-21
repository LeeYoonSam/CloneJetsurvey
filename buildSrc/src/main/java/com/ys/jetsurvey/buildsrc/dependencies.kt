package com.ys.jetsurvey.buildsrc

object Versions {
    const val ktlint = "0.39.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.3.1"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.0.9"

    const val junit = "junit:junit:4.13"

    const val material = "androidx.compose.material:material:1.2.1"

    object Accompanist {
        private const val version = "0.3.3.1"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:$version"
    }

    object Kotlin {
        private const val version = "1.6.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.5.1"
        const val coreKtx = "androidx.core:core-ktx:1.9.0"

        object Compose {
            const val snapshot = ""
            const val version = "1.2.1"

            @get:JvmStatic
            val snapshotUrl: String
                get() = "https://androidx.dev/snapshots/builds/$snapshot/artifacts/repository/"

            const val ui = "androidx.compose.ui:ui:$version"
            const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.ui:ui-tooling:$version"
            const val test = "androidx.compose.test:test-core:$version"
            const val uiTest = "androidx.ui:ui-test:$version"
        }

        object Navigation {
            private const val version = "2.5.2"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Material {
            private const val version = "1.7.0"
            const val material = "com.google.android.material:material:$version"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.3"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }

        object Lifecycle {
            private const val version = "2.5.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }
}