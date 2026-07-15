package fr.legendsofxania.dialog.entry.dialog.base

import com.github.retrooper.packetevents.protocol.dialog.DialogAction
import com.github.retrooper.packetevents.protocol.dialog.MultiActionDialog
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.Default
import com.typewritermc.core.extension.annotations.Entry
import fr.legendsofxania.dialog.entry.dialog.DialogBodyEntry
import fr.legendsofxania.dialog.entry.dialog.DialogEntryBase
import fr.legendsofxania.dialog.entry.dialog.DialogInputEntry
import fr.legendsofxania.dialog.entry.dialog.action.DialogButtonEntry
import org.bukkit.entity.Player
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Entry(
    "multi_action_dialog",
    "The base of a multi-action dialog",
    Colors.ORANGE,
    "material-symbols:splitscreen-top-outline-sharp"
)
class MultiActionDialogEntry(
    override val id: String = "",
    override val name: String = "",
    override val title: String = "",
    override val body: Ref<DialogBodyEntry> = emptyRef(),
    override val inputs: Ref<DialogInputEntry> = emptyRef(),
    override val afterAction: DialogAction = DialogAction.CLOSE,
    @Default("1")
    val columns: Int = 1,
    val buttons: List<Ref<DialogButtonEntry>> = emptyList(),
    val exitButton: Optional<Ref<DialogButtonEntry>> = Optional.empty(),
) : DialogEntryBase() {
    private val resolvedButtons: List<DialogButtonEntry> by lazy {
        buttons.map {
            it.get() ?: error("Could not find any button for dialog: $id")
        }
    }
    private val resolvedExitButton: DialogButtonEntry? by lazy {
        exitButton.get().get() ?: error("Could not find an en exit button for dialog: $id")
    }

    override fun build(player: Player): MultiActionDialog = MultiActionDialog(
        commonDialogData(player),
        resolvedButtons.map { it.build(player, id) },
        resolvedExitButton?.build(player, id),
        columns,
    )
}
