package fr.legendsofxania.dialog.entry.dialog.action

import com.github.retrooper.packetevents.protocol.dialog.action.DynamicCustomAction
import com.github.retrooper.packetevents.protocol.dialog.button.ActionButton
import com.github.retrooper.packetevents.protocol.dialog.button.CommonButtonData
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound
import com.github.retrooper.packetevents.protocol.nbt.NBTString
import com.github.retrooper.packetevents.resources.ResourceLocation
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Query
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.*
import com.typewritermc.core.interaction.InteractionContext
import com.typewritermc.core.interaction.InteractionContextBuilder
import com.typewritermc.core.interaction.context
import com.typewritermc.engine.paper.entry.ManifestEntry
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.EventEntry
import com.typewritermc.engine.paper.entry.triggerEntriesFor
import com.typewritermc.engine.paper.entry.triggerFor
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import fr.legendsofxania.dialog.entry.dialog.DialogEntryBase
import io.papermc.paper.connection.PlayerGameConnection
import io.papermc.paper.dialog.DialogResponseView
import io.papermc.paper.event.player.PlayerCustomClickEvent
import net.kyori.adventure.key.Key
import net.kyori.adventure.nbt.CompoundBinaryTag
import net.kyori.adventure.nbt.TagStringIO
import org.bukkit.entity.Player


@Tags("dialog_button")
@Entry(
    "dialog_button",
    "Represents a button in a dialog.",
    Colors.RED,
    "material-symbols:buttons-alt-outline-rounded"
)
class DialogButtonEntry(
    override val id: String = "",
    override val name: String = "",
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
    @Colored
    @Placeholder
    @Help("The label of the button that will be displayed.")
    val label: String = "",
    @Colored
    @Placeholder
    @MultiLine
    @Help("The tooltip to display when hovering over the button.")
    val tooltip: String = "",
    @Min(1)
    @Max(1024)
    @Default("200")
    @Help("The width of the button. Must be between 1 and 1024.")
    val width: Int = 200,
) : ManifestEntry, EventEntry {
    fun build(player: Player, dialog: String) = ActionButton(
        CommonButtonData(
            label.parsePlaceholders(player).asMini(),
            tooltip.parsePlaceholders(player).asMini(),
            width,
        ),
        DynamicCustomAction(
            ResourceLocation(Key.key("legendsofxania:dialog_button_click")),
            NBTCompound().apply {
                setTag("button", NBTString(id))
                setTag("dialog", NBTString(dialog))
            }
        ),
    )
}

@EntryListener(DialogButtonEntry::class)
fun handleClick(event: PlayerCustomClickEvent) {
    if (event.identifier != Key.key("legendsofxania:dialog_button_click")) return

    val connection = event.commonConnection as? PlayerGameConnection ?: return
    val player = connection.player

    val holder = event.tag ?: return
    val tag = TagStringIO.tagStringIO().asCompound(holder.string())

    val view = event.dialogResponseView ?: return
    val context = buildInteractionContext(view, tag)

    handleButton(tag, player, context)
}

private fun buildInteractionContext(
    view: DialogResponseView,
    tag: CompoundBinaryTag,
): InteractionContext {

    val dialog = Query.findById<DialogEntryBase>(
        tag.getString("dialog")
    ) ?: return context()

    val builder = InteractionContextBuilder()

    dialog.resolvedInputs.forEach { input ->
        with(input) {
            builder.apply(view)
        }
    }

    return builder.build()
}

private fun handleButton(
    tag: CompoundBinaryTag,
    player: Player,
    context: InteractionContext,
) {
    val button = Query.findById<DialogButtonEntry>(
        tag.getString("button")
    ) ?: return

    button.triggers.triggerEntriesFor(player, context)
}
