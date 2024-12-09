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
    mainClass.set("com.vizor.test.HelloApplication")
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
dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")
}
