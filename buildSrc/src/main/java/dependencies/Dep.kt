package dependencies

object BuildPlugins {
    const val gradlePluginVersion = "7.0.2"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$gradlePluginVersion"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Dagger.version}"
}

object Configs {
    const val compileSdkVersion = 31
    const val minSdkVersion = 21
    const val targetSdkVersion = 31
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Kotlin {
    const val version = "1.5.31"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1"
}

object Dagger {
    const val version = "2.38.1"
    const val hiltAndroid = "com.google.dagger:hilt-android:$version"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
}

object AndroidX {
    const val core = "androidx.core:core-ktx:1.6.0"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"

    const val fragment = "androidx.fragment:fragment-ktx:1.3.5"
    const val material = "com.google.android.material:material:1.4.0"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.1"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"

    object Lifecycle {
        private const val lifecycleVersion = "2.3.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    }

    object Navigation {
        private const val version = "2.3.5"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Room {
        private const val roomVersion = "2.3.0"
        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val roomKapt = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }
}

object Timber {
    const val timber = "com.jakewharton.timber:timber:4.7.1"
}

object Test {
    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.3"
    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:4.12.0"
}