plugins {
    application
    checkstyle
    jacoco
    id("org.sonarqube") version "7.0.1.6134"
}

group = "hexlet.code"
version = "1.0.0"

checkstyle {
    toolVersion = "10.12.4"
    configFile = file("checkstyle.xml")
    isIgnoreFailures = false
    maxWarnings = 0
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

sonar {
    properties {
        property("sonar.projectKey", "SaintCap_java-project-78")
        property("sonar.organization", "saintcap")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks {
    checkstyleMain {
        dependsOn(compileJava)
    }

    checkstyleTest {
        dependsOn(compileTestJava)
    }
}
