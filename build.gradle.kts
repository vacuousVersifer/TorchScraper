plugins {
    id("java")
}

group = "com.vacuousVersifier"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType(Jar::class) {
    manifest {
        attributes["Manifest-Version"] = "1.0"
        attributes["Main-Class"] = "Main"
    }
}

tasks.test {
    useJUnitPlatform()
}