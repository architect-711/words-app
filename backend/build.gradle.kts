plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "edu.architect-711"
version = System.getenv("BACKEND_VERSION")

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
	// https://mvnrepository.com/artifact/io.hypersistence/hypersistence-utils-hibernate-63
	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.9")

	// Api documentation
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.4.4") // frontend

	// https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
	runtimeOnly("org.flywaydb:flyway-database-postgresql:11.1.0") // db migration tool

	// validation
	implementation("org.springframework.boot:spring-boot-starter-validation:3.4.0")

	// Spring AOP
	implementation("org.springframework.boot:spring-boot-starter-aop:3.4.0")

	// For web (controllers, etc)
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Get rid off boilerplate code
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// testing
	testImplementation("org.springframework.boot:spring-boot-starter-test") // main test library
	testRuntimeOnly("org.junit.platform:junit-platform-launcher") // Junit
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	enabled = false
}