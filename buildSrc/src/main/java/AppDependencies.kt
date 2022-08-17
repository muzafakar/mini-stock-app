import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    private object Android {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleKtx}"
        const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleKtx}"
    }

    private object Kotlin {
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerialization}"
    }

    private object Compose {
        const val activity = "androidx.activity:activity-compose:1.3.1"
        const val ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
        const val material = "androidx.compose.material:material:${Versions.composeVersion}"
        const val previewTooling =
            "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
        const val jUnit = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
        const val testManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifeCycleKtx}"

        const val destination =
            "io.github.raamcosta.compose-destinations:core:${Versions.composeDestination}"
        const val destinationKsp =
            "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestination}"
    }

    private object Hilt{
        const val core = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }


    private val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private val serializatoinConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinSerializationConverter}"
    private val csvReader = "com.github.doyaaaaaken:kotlin-csv-jvm:${Versions.csvReader}"
    private val composeChart = "com.github.jaikeerthick:Composable-Graphs:${Versions.composeChart}"

    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(Android.coreKtx)
        add(Android.viewModel)
        add(Android.lifeCycleKtx)
        add(Kotlin.coroutines)
        add(Kotlin.serialization)
        add(Compose.activity)
        add(Compose.ui)
        add(Compose.material)
        add(Compose.previewTooling)
        add(Compose.viewModel)
        add(Compose.destination)
        add(retrofit)
        add(serializatoinConverter)
        add(gsonConverter)
        add(Hilt.core)
        add(csvReader)
        add(composeChart)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(Compose.jUnit)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(Kotlin.test)
    }

    val debugLibraries = arrayListOf<String>().apply {
        add(Compose.previewTooling)
        add(Compose.testManifest)
    }

    val symbolicLibraries = arrayListOf<String>().apply {
        add(Compose.destinationKsp)
    }

    val annotatedLibraries = arrayListOf<String>().apply {
        add(Hilt.compiler)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.ksp(list: List<String>) {
    list.forEach { dependecy ->
        add("ksp", dependecy)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}