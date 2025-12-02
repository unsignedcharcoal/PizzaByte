plugins {
    id("io.micronaut.application") version "4.6.1"
    id("com.gradleup.shadow") version "8.3.9"
    id("io.micronaut.test-resources") version "4.6.1"
    id("io.micronaut.aot") version "4.6.1"
}

version = "0.1"
group = "dev.carlos.soft.pizzabyte"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("org.projectlombok:lombok:1.18.40")

    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.redis:micronaut-redis-lettuce")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.views:micronaut-views-fieldset")
    implementation("io.micronaut.views:micronaut-views-thymeleaf")

    compileOnly("io.micronaut:micronaut-http-client")
    compileOnly("org.projectlombok:lombok:1.18.40")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("io.goodforgod:slf4j-simple-logger:2.0.0")
    runtimeOnly("org.yaml:snakeyaml")

    testImplementation("io.micronaut:micronaut-http-client")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    aotPlugins(platform("io.micronaut.platform:micronaut-platform:4.10.2"))
    aotPlugins("io.micronaut.security:micronaut-security-aot")
}


application {
    mainClass = "dev.carlos.soft.pizzabyte.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("25")
    targetCompatibility = JavaVersion.toVersion("25")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("dev.carlos.soft.pizzabyte.*")
    }
    testResources {
        additionalModules.add("jdbc-mysql")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
        configurationProperties.put("micronaut.security.jwks.enabled","false")
    }
}


tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "25"
}


