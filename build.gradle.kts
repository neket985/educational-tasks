import org.gradle.jvm.tasks.Jar

plugins {
    application
    kotlin("jvm") version "1.4.10"
}

group "ru.smirnov"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("me.tongfei:progressbar:0.9.5")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "14"
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "14"
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("ru.smirnov.educational.brother.SquareCrosswordTest")
}