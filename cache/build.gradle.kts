import dependencies.CacheDep

plugins {
    id(Config.Plugins.androidLibrary)
    id(Config.Plugins.kotlinAndroid)
    id(Config.Plugins.kotlinKapt)
}

android {

    namespace = Environments.Release.appId

    compileSdk = Config.Android.androidCompileSdkVersion
    buildToolsVersion = Config.Android.androidBuildToolsVersion

    defaultConfig {
        minSdk = Config.Android.androidMinSdkVersion

        testInstrumentationRunner = Config.testRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Modules
    implementation(project(Modules.data))
    // Kotlin
    implementation(CacheDep.kotlin)
    // JavaX
    implementation(CacheDep.javax)
    // Room
    CacheDep.room.forEach {
        api(it)
    }
    kapt(CacheDep.roomKapt)
    // Test Dependencies
    testImplementation(CacheDep.Test.junit)
    testImplementation(CacheDep.Test.assertJ)
    testImplementation(CacheDep.Test.coroutines)
    testImplementation(CacheDep.Test.testCore)
    testImplementation(CacheDep.Test.testExtJunit)
    testImplementation(CacheDep.Test.robolectric)
    testImplementation(CacheDep.Test.roomTest)
}