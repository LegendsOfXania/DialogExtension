package fr.legendsofxania.dialog.entry.dialog

import com.github.retrooper.packetevents.protocol.dialog.CommonDialogData
import com.github.retrooper.packetevents.protocol.dialog.Dialog
import com.github.retrooper.packetevents.protocol.dialog.DialogAction
import com.github.retrooper.packetevents.protocol.dialog.body.DialogBody
import com.github.retrooper.packetevents.protocol.dialog.input.Input
import com.typewritermc.core.entries.Entry
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Colored
import com.typewritermc.core.extension.annotations.Help
import com.typewritermc.core.extension.annotations.Placeholder
import com.typewritermc.core.extension.annotations.Tags
import com.typewritermc.core.interaction.InteractionContextBuilder
import com.typewritermc.engine.paper.entry.ManifestEntry
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import io.papermc.paper.dialog.DialogResponseView
import org.bukkit.entity.Player
import java.util.*

@Tags("dialog")
interface DialogEntry : ManifestEntry {
    override val id: String
    override val name: String

    @Colored
    @Placeholder
    @Help("The title of the dialog.")
    val title: String

    @Colored
    @Placeholder
    @Help("The external title of the dialog. If not present, the title will be used instead.")
    val externalTitle: Optional<String>

    @Help("The list of body elements")
    val body: Ref<DialogBodyEntry>

    @Help("The list of inputs elements")
    val inputs: Ref<DialogInputEntry>

    @Help("The action to perform after the dialog is closed.")
    val afterAction: DialogAction

    fun build(player: Player): Dialog
}

abstract class DialogEntryBase : DialogEntry {
    private val resolvedBody: List<DialogBodyEntry> by lazy { body.resolve() }
    val resolvedInputs: List<DialogInputEntry> by lazy { inputs.resolve() }

    protected fun commonDialogData(player: Player) = CommonDialogData(
        title.parsePlaceholders(player).asMini(),
        externalTitle.map { it.parsePlaceholders(player).asMini() }.orElse(null),
        true,
        false,
        afterAction,
        resolvedBody.map { it.build(player) },
        resolvedInputs.map { it.build(player) },
    )
}


@Tags("chainable")
interface ChainableEntry<T : ChainableEntry<T>> : Entry {
    @Help("The next entry in the chain.")
    val child: Ref<T>
}

fun <T : ChainableEntry<T>> Ref<T>.resolve(): List<T> {
    val result = mutableListOf<T>()
    var current: Ref<T> = this
    while (true) {
        val entry = current.get() ?: break
        result += entry
        current = entry.child
    }
    return result
}

@Tags("dialog_body")
interface DialogBodyEntry : ManifestEntry, ChainableEntry<DialogBodyEntry> {
    override val id: String
    override val name: String
    override val child: Ref<DialogBodyEntry>

    fun build(player: Player): DialogBody
}

@Tags("dialog_input")
interface DialogInputEntry : ManifestEntry, ChainableEntry<DialogInputEntry> {
    override val id: String
    override val name: String
    override val child: Ref<DialogInputEntry>

    @Colored
    @Placeholder
    @Help("The label of the input that will be displayed.")
    val label: String

    fun build(player: Player): Input
    fun InteractionContextBuilder.apply(view: DialogResponseView)
}
