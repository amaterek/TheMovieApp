package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.common.defaultPadding
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> DropdownMenuView(
    modifier: Modifier = Modifier,
    options: Map<T, String>,
    selected: MutableState<T>,
    expandedState: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    val textState = remember { mutableStateOf(options[selected.value].orEmpty()) }

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedState.value,
            onExpandedChange = { expandedState.value = !expandedState.value },
            Modifier.align(Alignment.Center),
        ) {
            val shape = RoundedCornerShape(defaultPadding / 2)
            Surface(
                elevation = 0.dp,
                shape = shape,
                modifier = Modifier
                    .padding(bottom = defaultPadding / 2, top = defaultPadding / 2)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onPrimary,
                        shape = shape,
                    ),
            ) {
                DropdownMenuItem(
                    onClick = { },
                ) {
                    Text(text = textState.value)
                }
            }
            ExposedDropdownMenu(
                expanded = expandedState.value,
                onDismissRequest = { expandedState.value = false },
            ) {
                options.forEach { (category, text) ->
                    DropdownMenuItem(
                        onClick = {
                            selected.value = category
                            textState.value = text
                            expandedState.value = false
                        }
                    ) {
                        Text(text = text)
                    }
                }
            }
        }
    }
}