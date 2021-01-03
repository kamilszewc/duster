import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
}


group = "pl.integrable"
version = "v0.1-alpha"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // JDBC Sqlite
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.xerial:sqlite-jdbc:3.25.2")

    // Spring JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // MySQL connector
    runtimeOnly("mysql:mysql-connector-java")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/kamilszewc/duster")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
//            groupId = "org.gradle.sample"
//            artifactId = "library"
//            version = "1.1"

            from(components["java"])
        }
    }
}

configurations {
    val elements = listOf(apiElements, runtimeElements)
    elements.forEach { element ->
        element.get().outgoing.artifacts.removeIf { it -> it.buildDependencies.getDependencies(null).contains(tasks.jar.get())}
        element.get().outgoing.artifact(tasks.bootJar.get())
    }
}