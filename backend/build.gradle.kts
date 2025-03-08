plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "edu.architect-711"
version = project.findProperty("BACKEND_VERSION") ?: "0.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
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
	// Database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // the orm specification, that adds required interfaces and how to use this tools
	runtimeOnly("org.postgresql:postgresql") // the database itself
	// https://mvnrepository.com/artifact/com.h2database/h2
	testImplementation("com.h2database:h2:2.3.232")


	// Api documentation
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools
	implementation("org.springframework.boot:spring-boot-devtools:3.4.1") // enhance development <speed

	// https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
	runtimeOnly("org.flywaydb:flyway-database-postgresql:11.1.0") // db migration tool

	// validation
	implementation("org.springframework.boot:spring-boot-starter-validation:3.4.0")

	// Security
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
	implementation("org.springframework.boot:spring-boot-starter-security")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")

	// Spring AOP
	implementation("org.springframework.boot:spring-boot-starter-aop:3.4.0")

	// For web (controllers, etc)
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Get rid off boilerplate code
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// testing
	testImplementation("org.springframework.boot:spring-boot-starter-test") // main test library
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher") // Junit
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	enabled = false
}