package fr.legendsofxania.dialog.entry.action

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerShowDialog
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.core.extension.annotations.Help
import com.typewritermc.engine.paper.entry.Criteria
import com.typewritermc.engine.paper.entry.Modifier
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger
import com.typewritermc.engine.paper.entry.entries.ConstVar
import com.typewritermc.engine.paper.entry.entries.Var
import com.typewritermc.engine.paper.extensions.packetevents.sendPacket
import fr.legendsofxania.dialog.entry.dialog.DialogEntry

@Entry(
    "show_dialog_action",
    "Show a dialog to a player",
    Colors.RED,
    "material-symbols:imagesmode-outline-rounded"
)
class ShowDialogActionEntry(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
    @Help("The dialog to show.")
    val dialog: Var<Ref<DialogEntry>> = ConstVar(emptyRef())
) : ActionEntry {
    override fun ActionTrigger.execute() {
        player.sendPacket(
            WrapperPlayServerShowDialog(
                dialog.get(player).entry?.build(player) ?: error("Could not find dialog for entry: $id")
            )
        )
    }
}
