plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
}

group = "io.github.ZeronDev"
version = "1.0.0"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://libraries.minecraft.net")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.9.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation(kotlin("reflect"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    javadoc {
        options.encoding = "UTF-8"
    }
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")
        from("$buildDir/dokka/html")
    }

    create<Jar>("paperJar") {
        from(sourceSets["main"].output)

        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())

        doLast {
            copy {
                from(archiveFile)
                val fileLoc = File("C:\\Users\\USER\\Desktop\\ZeronPlayGround\\plugins")
                into(if (File(fileLoc, archiveFileName.get()).exists()) fileLoc else fileLoc)
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("ZeronLib")
                description.set("Minecraft library for Zeron")
                url.set("https://github.com/ZeronDev/ZeronLib")
                licenses {
                    license {
                        name.set("GNU General Public License Version 3")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("Zeron")
                        description.set("Minecraft Plugin Developer")
                        email.set("zeron_0304@naver.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/ZeronDev/ZeronLib.git")
                    developerConnection.set("scm:git:https://github.com/ZeronDev/ZeronLib.git")
                    url.set("https://github.com/ZeronDev/ZeronLib.git")
                }
            }
        }
    }
    repositories {
        maven {
//            credentials {
//
//            }
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
}

