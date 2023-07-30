import org.gradle.kotlin.dsl.execution.ProgramText

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.2"
    id("dependencies")
}
val ktor: (String) -> String by extra
val koin: (String) -> String by extra
val kotlin: (String) -> String by extra
val exposed: (String) -> String by extra
val kotlinx: (String) -> String by extra

group = "com.codrut"
version = "0.0.1"
application {
    mainClass.set("com.codrut.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}


dependencies {
    val logback_version: String by project
    val h2_version: String by project

    // Core dependencies
    implementation(kotlinx("coroutines-core"))

    // Ktor Dependencies
    implementation(ktor("core-jvm"))
    implementation(ktor("netty-jvm"))

    // DI dependencies
    implementation(koin("ktor"))
    implementation(koin("logger-slf4j"))

    // DB dependencies
    implementation(exposed("core"))
    implementation(exposed("dao"))
    implementation(exposed("jdbc"))
    implementation(exposed("java-time"))
    implementation("com.h2database:h2:$h2_version")

    // Misc Dependencies
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Testing Dependencies
    testImplementation(ktor("tests-jvm"))
    testImplementation(kotlin("test-junit"))
}