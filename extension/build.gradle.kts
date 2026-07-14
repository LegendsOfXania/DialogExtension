plugins {
    kotlin("jvm") version "2.2.10"
    id("com.typewritermc.module-plugin") version "2.1.0"
}

group = "fr.legendsofxania"
version = "0.0.1"

repositories {}
dependencies {}

typewriter {
    namespace = "legendsofxania"
    extension {
        name = "Dialog"
        shortDescription = "Display dialogs in Typewriter."
        description = """
            Easily create custom dialogs, display them to your players,
            and improve your interaction with your players.
            Created by the Legends of Xania.
        """.trimIndent()
        engineVersion = "0.9.0-beta-174"
        channel = com.typewritermc.moduleplugin.ReleaseChannel.BETA
        paper()
    }
}

kotlin {
    jvmToolchain(21)
}
