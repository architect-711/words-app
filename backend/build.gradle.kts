plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "edu.architect-711"
version = project.findProperty("APP_VERSION") ?: "0.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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