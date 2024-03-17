import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    id("org.sonarqube") version "4.4.1.3373"
}

group = "br.com.fiap.postech"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

sonar {
    properties {
        property("sonar.projectKey", "postech-food-challenge_food-challenge")
        property("sonar.organization", "postech-food-challenge")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

dependencies {
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.hypersistence:hypersistence-utils-hibernate-62:3.6.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation ("com.auth0:jwks-rsa:0.11.0")
    implementation("com.auth0:java-jwt:4.0.0")
    implementation("software.amazon.awssdk:cognitoidentityprovider:2.25.6")
    implementation("com.amazonaws:aws-java-sdk-cognitoidentity:1.12.676")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
