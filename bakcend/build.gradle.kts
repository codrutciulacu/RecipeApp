plugins {
    kotlin("jvm") version "1.9.0"
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("plugin.spring") version "1.8.22"
}

group = "com.codrut"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")
    apply(plugin="org.springframework.boot")
    apply(plugin="io.spring.dependency-management")
    apply(plugin="org.jetbrains.kotlin.plugin.spring")

    repositories {
        mavenCentral()
    }

    dependencies {

    }
}
