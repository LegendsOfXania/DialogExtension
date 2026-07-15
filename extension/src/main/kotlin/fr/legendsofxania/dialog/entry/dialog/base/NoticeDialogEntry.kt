package fr.legendsofxania.dialog.entry.dialog.base

import com.github.retrooper.packetevents.protocol.dialog.DialogAction
import com.github.retrooper.packetevents.protocol.dialog.NoticeDialog
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
import kotlin.jvm.optionals.getOrNull

@Entry(
    "notice_dialog",
    "The base of a notice dialog",
    Colors.ORANGE,
    "material-symbols:splitscreen-top-outline-sharp"
)
class NoticeDialogEntry(
    override val id: String = "",
    override val name: String = "",
    override val title: String = "",
    override val body: Ref<DialogBodyEntry> = emptyRef(),
    override val inputs: Ref<DialogInputEntry> = emptyRef(),
    override val afterAction: DialogAction = DialogAction.CLOSE,
    val exitButton: Optional<Ref<DialogButtonEntry>> = Optional.empty(),
) : DialogEntryBase() {
    val resolvedExitButton: DialogButtonEntry? by lazy { exitButton.getOrNull()?.get() }

    override fun build(player: Player) = NoticeDialog(
        commonDialogData(player),
        resolvedExitButton?.build(player, id) ?: NoticeDialog.DEFAULT_ACTION,
    )
}
