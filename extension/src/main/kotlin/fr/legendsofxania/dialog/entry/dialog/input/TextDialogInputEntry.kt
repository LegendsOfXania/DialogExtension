package fr.legendsofxania.dialog.entry.dialog.input

import com.github.retrooper.packetevents.protocol.dialog.input.Input
import com.github.retrooper.packetevents.protocol.dialog.input.TextInputControl
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
import java.util.*
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass

@Entry(
    "text_dialog_input",
    "Set a text input in a dialog",
    Colors.YELLOW,
    "fa6-solid:keyboard"
)
@ContextKeys(TextDialogInputContextKey::class)
class TextDialogInputEntry(
    override val id: String = "",
    override val name: String = "",
    override val child: Ref<DialogInputEntry> = emptyRef(),
    override val label: String = "",
    @Help("Whether the label should be visible.")
    @Default("true")
    val labelVisible: Boolean = true,
    @Min(1)
    @Max(1024)
    @Help("The width of the input. Must be between 1 and 1024.")
    @Default("200")
    val width: Int = 200,
    @Min(1)
    @Max(512)
    @Help("The height of the input. Must be between 1 and 512.")
    @Default("200")
    val height: Int = 100,
    @Help("The initial text to display.")
    val initial: String = "",
    @Help("The maximum number of lines to display.")
    val maxLines: Optional<Int> = Optional.empty(),
    @Min(1)
    @Help("The maximum length of the input. Must be between 1 and 32.")
    @Default("32")
    val maxLength: Int = 32,
) : DialogInputEntry {
    override fun build(player: Player) = Input(
        id,
        TextInputControl(
            width,
            label.parsePlaceholders(player).asMini(),
            labelVisible,
            initial,
            maxLength,
            TextInputControl.MultilineOptions(maxLines.getOrNull(), height)
        )
    )

    override fun InteractionContextBuilder.apply(view: DialogResponseView) {
        this@TextDialogInputEntry[TextDialogInputContextKey.VALUE] =
            view.getText(id) ?: label
    }
}

enum class TextDialogInputContextKey(override val klass: KClass<*>) : EntryContextKey {
    @KeyType(String::class)
    VALUE(String::class),
}
