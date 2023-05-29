plugins {
    val agpVersion = "8.2.0-alpha05"
    id("com.android.application") version agpVersion apply false
    id("com.android.library") version agpVersion apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.dagger.hilt.android") version "2.46" apply false
}


//task clean(type: Delete) {
//    delete rootProject.buildDir
//}