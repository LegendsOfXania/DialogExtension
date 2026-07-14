package fr.legendsofxania.dialog.entry.dialog.base

import com.github.retrooper.packetevents.protocol.dialog.ConfirmationDialog
import com.github.retrooper.packetevents.protocol.dialog.DialogAction
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.Entry
import fr.legendsofxania.dialog.entry.dialog.DialogBodyEntry
import fr.legendsofxania.dialog.entry.dialog.DialogEntryBase
import fr.legendsofxania.dialog.entry.dialog.DialogInputEntry
import fr.legendsofxania.dialog.entry.dialog.action.DialogButtonEntry
import org.bukkit.entity.Player
import java.util.*

@Entry(
    "confirmation_dialog",
    "The base of a confirmation dialog",
    Colors.ORANGE,
    "material-symbols:splitscreen-top-outline-sharp"
)
class ConfirmationDialogEntry(
    override val id: String = "",
    override val name: String = "",
    override val title: String = "",
    override val externalTitle: Optional<String> = Optional.empty(),
    override val body: Ref<DialogBodyEntry> = emptyRef(),
    override val inputs: Ref<DialogInputEntry> = emptyRef(),
    override val afterAction: DialogAction = DialogAction.CLOSE,
    val yes: Ref<DialogButtonEntry> = emptyRef(),
    val no: Ref<DialogButtonEntry> = emptyRef(),
) : DialogEntryBase() {
    val resolvedYes: DialogButtonEntry by lazy { yes.get() ?: error("Could not find a yes button for dialog: $id") }
    val resolvedNo: DialogButtonEntry by lazy { no.get() ?: error("Could not find a no button for dialog: $id") }

    override fun build(player: Player) = ConfirmationDialog(
        commonDialogData(player),
        resolvedYes.build(player, id),
        resolvedNo.build(player, id),
    )
}
