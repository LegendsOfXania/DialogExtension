package fr.legendsofxania.dialog.entry.dialog.input

import com.github.retrooper.packetevents.protocol.dialog.input.Input
import com.github.retrooper.packetevents.protocol.dialog.input.SingleOptionInputControl
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.*
import com.typewritermc.core.interaction.EntryContextKey
import com.typewritermc.core.interaction.InteractionContextBuilder
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import fr.legendsofxania.dialog.entry.dialog.DialogInputEntry
import io.papermc.paper.dialog.DialogResponseView
import org.bukkit.entity.Player
import kotlin.reflect.KClass

@Entry(
    "single_option_dialog_input",
    "Set a single option input in a dialog",
    Colors.YELLOW,
    "material-symbols:library-add-check-outline-rounded"
)
@ContextKeys(SingleOptionDialogInputContextKey::class)
class SingleOptionDialogInputEntry(
    override val id: String = "",
    override val name: String = "",
    override val child: Ref<DialogInputEntry> = emptyRef(),
    override val label: String = "",
    @Help("Whether the label should be visible.")
    @Default("true")
    val labelVisible: Boolean = true,
    @Min(1)
    @Max(1024)
    @Default("200")
    @Help("The width of the input. Must be between 1 and 1024.")
    val width: Int = 200,
    @Help("The list of options to display. The first option will be selected by default.")
    val entries: List<Option> = emptyList(),
) : DialogInputEntry {
    override fun build(player: Player) = Input(
        id,
        SingleOptionInputControl(
            width,
            entries.mapIndexed { index, entry ->
                SingleOptionInputControl.Entry(
                    entry.id,
                    entry.display.asMini(),
                    index == 0,
                )
            },
            label.parsePlaceholders(player).asMini(),
            labelVisible,
        )
    )

    override fun InteractionContextBuilder.apply(view: DialogResponseView) {
        val selectedId = view.getText(id)
        val selectedDisplay = entries.find { it.id == selectedId }?.display ?: selectedId

        this@SingleOptionDialogInputEntry[SingleOptionDialogInputContextKey.VALUE] =
            selectedDisplay ?: label
    }
}

data class Option(
    @Generated
    @Help("The unique identifier of the option.")
    val id: String = "",
    @Colored
    @Help("The display text of the option.")
    val display: String = "",
)

enum class SingleOptionDialogInputContextKey(override val klass: KClass<*>) : EntryContextKey {
    @KeyType(String::class)
    VALUE(String::class),
}
