plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
}

group = "com.github.finbot.explicit"
version = "0.0.5"

dependencies {
    compileOnly("io.gitlab.arturbosch.detekt:detekt-api:1.23.5")
    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.23.5")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

kotlin {
    jvmToolchain(8)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
    systemProperty("compile-snippet-tests", project.hasProperty("compile-test-snippets"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
