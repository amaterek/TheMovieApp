package amaterek.movie.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val AppBarHeight = 56.dp

val LoadingIndicatorHeight = 40.dp
val LoadingIndicatorPadding = 4.dp
val LoadingIndicatorAlignment = Alignment.Center
val LoadingIndicatorColor: Color
    @Composable get() = MaterialTheme.colors.secondary
