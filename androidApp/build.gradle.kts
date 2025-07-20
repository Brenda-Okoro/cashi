plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.cashi.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cashi.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all {
                it.useJUnitPlatform()
            }
        }
    }
}

dependencies {
    implementation(project(":shared"))

    // Compose BOM for version alignment
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    //Ktor
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")

    //okhttp
    implementation("io.ktor:ktor-client-okhttp:2.3.5")

    // Koin
    implementation("io.insert-koin:koin-android:3.4.3")
    implementation("io.insert-koin:koin-androidx-compose:3.4.6")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Debug tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Cucumber dependencies
    testImplementation("io.cucumber:cucumber-java:7.14.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.14.0")

    // Kotest dependencies
    testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")

    // JUnit Platform
    testImplementation("org.junit.platform:junit-platform-suite:1.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("androidx.test.ext:junit:1.1.5")

    // Android Test dependencies
    testImplementation ("androidx.test.ext:junit:1.1.5")
    testImplementation ("androidx.test:runner:1.5.2")
    testImplementation ("androidx.test:rules:1.5.0")
    testImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Appium dependencies
    testImplementation("io.appium:java-client:8.5.1")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.9.1")
}