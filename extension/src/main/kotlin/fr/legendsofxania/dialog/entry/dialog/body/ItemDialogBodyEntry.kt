package fr.legendsofxania.dialog.entry.dialog.body

import com.github.retrooper.packetevents.protocol.dialog.body.ItemDialogBody
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.*
import com.typewritermc.engine.paper.utils.item.Item
import fr.legendsofxania.dialog.entry.dialog.DialogBodyEntry
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.entity.Player

@Entry(
    "item_dialog_body",
    "Set an item in a dialog",
    Colors.YELLOW,
    "streamline:give-gift-solid",
)
class ItemDialogBodyEntry(
    override val id: String = "",
    override val name: String = "",
    override val child: Ref<DialogBodyEntry> = emptyRef(),
    @Help("The item to display in the dialog.")
    val item: Item = Item.Empty,
    @Help("Whether to show decorations around the item.")
    val showDecorations: Boolean = true,
    @Help("Whether to show a tooltip when hovering over the item.")
    val showTooltip: Boolean = true,
    @Min(1)
    @Max(256)
    @Default("16")
    @Help("The width of the input. Must be between 1 and 256.")
    val width: Int = 16,
    @Min(1)
    @Max(256)
    @Default("16")
    @Help("The height of the input. Must be between 1 and 256.")
    val height: Int = 16,
) : DialogBodyEntry {
    override fun build(player: Player) = ItemDialogBody(
        SpigotConversionUtil.fromBukkitItemStack(item.build(player)),
        null,
        showDecorations,
        showTooltip,
        width,
        height,
    )
}
