plugins {
    kotlin("jvm") version "1.9.24"
    `maven-publish`
}

group = "com.github.finbot.explicit"
version = "0.0.4"

dependencies {
    compileOnly("io.gitlab.arturbosch.detekt:detekt-api:1.23.5")
    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.23.7")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
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
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/GaniKaraoglu/detekt-explicit")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
