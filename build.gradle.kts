plugins {
	java
	id("org.springframework.boot") version "3.2.6"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "springai"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

val opennlpVersion = "1.9.3"
val dl4jVersion = "1.0.0-beta7"

extra["springAiVersion"] = "1.0.0-M1"

dependencies {
	implementation("com.theokanning.openai-gpt3-java:client:0.10.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")

	// SQLite JDBC driver
	implementation("org.xerial:sqlite-jdbc:3.34.0")

	implementation("org.deeplearning4j:deeplearning4j-core:$dl4jVersion")
	implementation("org.deeplearning4j:deeplearning4j-nlp:$dl4jVersion")
	implementation("org.nd4j:nd4j-native-platform:$dl4jVersion")

	// Apache OpenNLP for text preprocessing
	implementation("org.apache.opennlp:opennlp-tools:$opennlpVersion")
	implementation("org.apache.opennlp:opennlp-uima:$opennlpVersion")

	// TxtAi Java
	implementation("com.github.neuml:txtai.java:v7.2.0")

	// Jsoup
	implementation("org.jsoup:jsoup:1.17.2")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}
