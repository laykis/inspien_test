import org.gradle.internal.declarativedsl.dom.mutation.mutationArguments

plugins {
    id("java")
    id("com.github.bjornvester.wsdl2java") version "2.0.2"
}

group = "inspien"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    //wsdl 객체 생성용
    implementation("org.apache.cxf:cxf-rt-frontend-jaxws:4.1.0")
    implementation("org.apache.cxf:cxf-rt-transports-http:4.1.0")

    //soap 메시지 생성용
    implementation("jakarta.xml.soap:jakarta.xml.soap-api:3.0.0")
    implementation("com.sun.xml.messaging.saaj:saaj-impl:3.0.2")

    //jaxb
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
    implementation("org.glassfish.jaxb:jaxb-runtime:3.0.2")

    //oracle
    implementation("com.oracle.database.jdbc:ojdbc8:19.8.0.0")

    // FTP
    implementation("commons-net:commons-net:3.11.1")

    // JSON 파싱
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.2")



}

tasks.test {
    useJUnitPlatform()
}

wsdl2java {
    wsdlDir.set(file("src/main/resources/wsdl")) // WSDL 파일이 위치한 디렉터리
    generatedSourceDir.set(file("build/generated-sources/wsdl")) // 생성된 소스 코드 위치

    // 변환할 WSDL 파일 목록 (파일명을 정확히 입력)
    includes.set(listOf("InspienGetRecruitingTestServicesInfo.wsdl"))

    // WSDL에서 생성될 패키지 이름
    markGenerated.set(true)
    packageName.set("com.example.generated")



}

sourceSets {
    main {
        java.srcDir("/generated-sources/wsdl")
    }
}

tasks.register("addXmlRootElement") {
    dependsOn("wsdl2java")

    doLast {
        val generatedSourcesDir = file("build/generated-sources/wsdl")
        val files = generatedSourcesDir.walk().filter { it.isFile && it.extension == "java" }

        files.forEach { file ->
            val content = file.readText()
            if (!content.contains("@XmlRootElement")) {  // 이미 추가된 경우 건너뜀
                val updatedContent = content.replace(
                    "@XmlType(",
                    "@XmlRootElement\n@XmlType("
                )
                file.writeText(updatedContent)
                println("Updated: ${file.name}")
            }
        }
    }
}
