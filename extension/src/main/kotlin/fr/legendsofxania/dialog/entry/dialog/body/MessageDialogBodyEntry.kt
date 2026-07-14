package fr.legendsofxania.dialog.entry.dialog.body

import com.github.retrooper.packetevents.protocol.dialog.body.PlainMessage
import com.github.retrooper.packetevents.protocol.dialog.body.PlainMessageDialogBody
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.*
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import fr.legendsofxania.dialog.entry.dialog.DialogBodyEntry
import org.bukkit.entity.Player

@Entry(
    "message_dialog_body",
    "Set a text in a dialog",
    Colors.YELLOW,
    "ic:baseline-comment-bank",
)
class MessageDialogBodyEntry(
    override val id: String = "",
    override val name: String = "",
    override val child: Ref<DialogBodyEntry> = emptyRef(),
    @Colored
    @Placeholder
    @MultiLine
    @Help("The message to display in the dialog.")
    val message: String = "",
    @Min(1)
    @Max(1024)
    @Default("200")
    @Help("The width of the input. Must be between 1 and 1024.")
    val width: Int = 200,
) : DialogBodyEntry {
    override fun build(player: Player) = PlainMessageDialogBody(
        PlainMessage(
            message.parsePlaceholders(player).asMini(),
            width,
        )
    )
}
