plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.berlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.0-M1")
    testImplementation(kotlin("test"))
    implementation("com.opencsv:opencsv:5.7.1")
    implementation("io.insert-koin:koin-core:4.0.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}