plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

import com.android.build.api.variant.ScopedArtifacts
import com.android.build.api.artifact.ScopedArtifact

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import javassist.ClassPool
import javassist.CtClass
import java.io.FileInputStream

abstract class AddClassesTask: DefaultTask() {

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @TaskAction
    fun taskAction() {
        val pool = ClassPool(ClassPool.getDefault())

        val interfaceClass = pool.makeInterface("com.android.api.tests.SomeInterface");
        println("Adding $interfaceClass")

        interfaceClass.writeFile(output.get().asFile.absolutePath)
    }
}

androidComponents {
    onVariants { variant ->
        val taskProvider = project.tasks.register<AddClassesTask>("${variant.name}AddClasses")
        variant.artifacts
            .forScope(ScopedArtifacts.Scope.PROJECT)
            .use(taskProvider)
            .toAppend(
                ScopedArtifact.CLASSES,
                AddClassesTask::output
            )
    }
}

android {
    compileSdk = 32

    namespace = "com.test.toappend"

    defaultConfig {
        applicationId = "com.test.toappend"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.2.3"

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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}