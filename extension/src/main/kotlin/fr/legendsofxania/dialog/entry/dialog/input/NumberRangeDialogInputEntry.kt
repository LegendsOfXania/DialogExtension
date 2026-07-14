package fr.legendsofxania.dialog.entry.dialog.input

import com.github.retrooper.packetevents.protocol.dialog.input.Input
import com.github.retrooper.packetevents.protocol.dialog.input.NumberRangeInputControl
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
    "number_range_dialog_input",
    "Set a number range input in a dialog",
    Colors.YELLOW,
    "material-symbols:arrow-range-rounded"
)
@ContextKeys(NumberRangeDialogInputContextKey::class)
class NumberRangeDialogInputEntry(
    override val id: String = "",
    override val name: String = "",
    override val child: Ref<DialogInputEntry> = emptyRef(),
    override val label: String = "",
    @Help("A translation key to be used for building label. Defaults to 'options.generic_value'.")
    val labelFormat: Optional<String> = Optional.empty(),
    @Min(1)
    @Max(1024)
    @Default("200")
    @Help("The width of the input. Must be between 1 and 1024.")
    val width: Int = 200,
    @Help("The minimum value of the range.")
    val minValue: Float = 0F,
    @Default("100")
    @Help("The maximum value of the range.")
    val maxValue: Float = 100F,
    @Help("The initial value of the range.")
    val initialValue: Optional<Float> = Optional.empty(),
    @Help("The step size of the range. (If present, only values of Initial Value + value * step will be allowed)")
    val step: Optional<Float> = Optional.empty(),
) : DialogInputEntry {
    override fun build(player: Player) = Input(
        id,
        NumberRangeInputControl(
            width,
            label.parsePlaceholders(player).asMini(),
            labelFormat.orElse("options.generic_value"),
            NumberRangeInputControl.RangeInfo(
                minValue,
                maxValue,
                initialValue.getOrNull(),
                step.getOrNull(),
            )
        )
    )

    override fun InteractionContextBuilder.apply(view: DialogResponseView) {
        this@NumberRangeDialogInputEntry[NumberRangeDialogInputContextKey.VALUE] =
            view.getFloat(id) ?: initialValue.getOrNull() ?: minValue
    }
}

enum class NumberRangeDialogInputContextKey(override val klass: KClass<*>) : EntryContextKey {
    @KeyType(Float::class)
    VALUE(Float::class),
}
