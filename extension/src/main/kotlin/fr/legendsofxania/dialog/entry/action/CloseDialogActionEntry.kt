package fr.legendsofxania.dialog.entry.action

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerClearDialog
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.Criteria
import com.typewritermc.engine.paper.entry.Modifier
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger
import com.typewritermc.engine.paper.extensions.packetevents.sendPacket

@Entry(
    "close_dialog_action",
    "Close the current opened dialog",
    Colors.RED,
    "material-symbols:image-not-supported-outline-rounded"
)
class CloseDialogActionEntry(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
) : ActionEntry {
    override fun ActionTrigger.execute() {
        player.sendPacket(WrapperPlayServerClearDialog())
    }
}
