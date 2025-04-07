import java.io.FileInputStream
import java.util.*

plugins {
    java
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
}

group = "borikkori.community"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jsoup:jsoup:1.19.1")
    //메일
    implementation("org.springframework.boot:spring-boot-starter-mail")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")


    // https://mvnrepository.com/artifact/org.mapstruct/mapstruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    //Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    //Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    implementation(kotlin("script-runtime"))
}
val props = Properties()
FileInputStream("src/main/resources/gradle.properties").use { props.load(it) }

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("spring.datasource.url", props.getProperty("DB_URL") as String)
    systemProperty("spring.datasource.username", props.getProperty("DB_USER_NAME") as String)
    systemProperty("spring.datasource.password", props.getProperty("DB_PASSWORD") as String)
    systemProperty("spring.mail.port", props.getProperty("MAIL_PORT") as String)
    systemProperty("spring.mail.username", props.getProperty("MAIL_USER_NAME") as String)
    systemProperty("spring.mail.password", props.getProperty("MAIL_PASSWORD") as String)
    systemProperty("secret-key", props.getProperty("JWT_KEY") as String)
    systemProperty("spring.data.redis.host", props.getProperty("REDIS_HOST") as String)
    systemProperty("spring.data.redis.port", props.getProperty("REDIS_PORT") as String)
    systemProperty("cors.allowed.origins", props.getProperty("CORS_ORIGIN") as String)
}
