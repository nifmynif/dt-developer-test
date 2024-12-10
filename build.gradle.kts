plugins {
    id("java")
    id("application")
    id("org.javamodularity.moduleplugin") version ("1.8.12")
    id("org.openjfx.javafxplugin") version ("0.1.0")
}

group = "com.vizor.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.vizor.test.MainWindow")
}

buildscript {
    repositories {
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.openjfx:javafx-plugin:0.1.0")
    }
}
apply(plugin = "org.openjfx.javafxplugin")
javafx {
    version = "19.0.2"
    modules("javafx.controls", "javafx.fxml")
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

val lombokVersion = "1.18.36"
val slf4jVersion = "2.0.16"
val logbackjVersion = "1.4.12"
val junitVersion = "5.10.2"

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-classic:$logbackjVersion")
    implementation ("org.junit.jupiter:junit-jupiter-api:$junitVersion")

    testImplementation("ch.qos.logback:logback-classic:$logbackjVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")

    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.test {
    useJUnitPlatform()
}