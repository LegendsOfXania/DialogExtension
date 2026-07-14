package fr.legendsofxania.dialog.entry.dialog.input

import com.github.retrooper.packetevents.protocol.dialog.input.BooleanInputControl
import com.github.retrooper.packetevents.protocol.dialog.input.Input
import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.extension.annotations.ContextKeys
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.core.extension.annotations.Help
import com.typewritermc.core.extension.annotations.KeyType
import com.typewritermc.core.interaction.EntryContextKey
import com.typewritermc.core.interaction.InteractionContextBuilder
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.asMini
import fr.legendsofxania.dialog.entry.dialog.DialogInputEntry
import io.papermc.paper.dialog.DialogResponseView
import org.bukkit.entity.Player
import kotlin.reflect.KClass

@Entry(
    "boolean_dialog_input",
    "Set a boolean input in a dialog",
    Colors.YELLOW,
    "material-symbols:check-box-outline"
)
@ContextKeys(BooleanDialogInputContextKey::class)
class BooleanDialogInputEntry(
    override val id: String = "",
    override val name: String = "",
    override val child: Ref<DialogInputEntry> = emptyRef(),
    override val label: String = "",
    @Help("The initial value of the boolean.")
    val initialValue: Boolean = false,
) : DialogInputEntry {
    override fun build(player: Player) = Input(
        id,
        BooleanInputControl(
            label.parsePlaceholders(player).asMini(),
            initialValue,
            "true",
            "false",
        )
    )

    override fun InteractionContextBuilder.apply(view: DialogResponseView) {
        this@BooleanDialogInputEntry[BooleanDialogInputContextKey.VALUE] =
            view.getBoolean(id) ?: initialValue
    }
}

enum class BooleanDialogInputContextKey(override val klass: KClass<*>) : EntryContextKey {
    @KeyType(Boolean::class)
    VALUE(Boolean::class),
}
