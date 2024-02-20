plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.bitirmeprojesi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bitirmeprojesi"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    packaging {
        resources.excludes.add("META-INF/native-image/native-image.properties")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.10.1")
    //implementation("com.google.cloud:libraries-bom:26.32.0")
    //implementation("com.google.cloud:google-cloud-spanner-jdbc:2.15.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}