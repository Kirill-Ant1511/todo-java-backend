plugins {
    id("java")
    id("org.springframework.boot") version ("4.0.2")
    id("io.spring.dependency-management") version ("1.1.7")
}

group = "pal.comp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-lib"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")

    // JJWT API - compile-time dependency
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")

    // JJWT Implementation & Jackson serializer - runtime-only
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Optional: If you prefer Gson instead of Jackson:
    runtimeOnly("io.jsonwebtoken:jjwt-gson:0.11.5")
}

tasks.test {
    useJUnitPlatform()
}