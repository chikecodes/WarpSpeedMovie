package dependencies

object UiDep {

    // Kotlin
    const val kotlin = Dependencies.KotlinDep.kotlin

    // Core
    const val coreKtx = Dependencies.CoreDep.coreKtx
    const val appCompat = Dependencies.CoreDep.appCompat
    const val constraint = Dependencies.CoreDep.constraint
    const val navigationFragmentKtx = Dependencies.CoreDep.navigationFragmentKtx
    const val navigationUiKtx = Dependencies.CoreDep.navigationUiKtx
    const val activityKtx = Dependencies.CoreDep.activityKtx


    val Compose = listOf(
        Dependencies.ComposeDep.ui,
        Dependencies.ComposeDep.material,
        Dependencies.ComposeDep.uiToolingPreview,
        Dependencies.ComposeDep.uiTooling,
        Dependencies.ComposeDep.activityCompose,
        Dependencies.ComposeDep.lifecycle,
        Dependencies.ComposeDep.hiltNavigationCompose,
        Dependencies.ComposeDep.composeLiveData,
        Dependencies.ComposeDep.composeCoil
    )

    // LifeCycle
    val LifeCycle = listOf(
        Dependencies.LifeCycleDep.viewModelKtx,
        Dependencies.LifeCycleDep.lifeCycleRuntime,
        Dependencies.LifeCycleDep.lifeCycleRuntimeKtx
    )

    // Hilt
    val DaggerHilt = listOf(
        Dependencies.DaggerHiltDep.hiltAndroid
    )

    val DaggerHiltKapt = listOf(
        Dependencies.DaggerHiltDep.hiltAndroidKapt,
        Dependencies.DaggerHiltDep.hiltKapt
    )

    // Coroutines
    val Coroutines = listOf(
        Dependencies.CoroutinesDep.coroutineCore,
        Dependencies.CoroutinesDep.coroutineAndroid
    )

    const val glide = Dependencies.GlideDep.glide
    const val glideKapt = Dependencies.GlideDep.glideKapt
    const val timber = Dependencies.TimberDep.timber
    const val lottie = Dependencies.LottieDep.lottie

    object Test {
        const val junit = Dependencies.TestDep.junit
        const val coroutines = Dependencies.TestDep.coroutinesTest
        const val mockitoKotlin = Dependencies.TestDep.mockitoKotlin
        const val mockitoInline = Dependencies.TestDep.mockitoInline
        const val assertJ = Dependencies.TestDep.assertJ
        const val androidxArchCore = Dependencies.TestDep.androidxArchCore
        const val robolectric = Dependencies.TestDep.robolectric
        const val testExtJunit = Dependencies.TestDep.testExtJunit
    }
}