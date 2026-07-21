package fr.legendsofxania.dialog

import com.github.retrooper.packetevents.manager.server.ServerVersion
import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.logger
import com.typewritermc.engine.paper.utils.serverVersion

@Singleton
class Initializer : Initializable {
    override suspend fun initialize() {
        if (serverVersion.isOlderThan(ServerVersion.V_1_21_8)) {
            logger.severe(
                    "This server is running on a version that doesn't support dialogs features. The extension won't work as expected."
            )
        }
    }

    override suspend fun shutdown() {}
}
