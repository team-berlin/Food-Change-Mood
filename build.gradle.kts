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
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("com.google.truth:truth:1.4.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}