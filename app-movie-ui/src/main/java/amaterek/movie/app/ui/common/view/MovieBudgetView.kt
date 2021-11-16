package amaterek.movie.app.ui.common.view

import amaterek.movie.app.ui.R
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
fun MovieBudgetView(
    budget: Long,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
) {
    Text(
        text = stringResource(R.string.movie_details_budget, budget / 1_000_000f),
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
    )
}

@Preview
@Composable
fun MovieBudgetViewPreview() {
    MovieBudgetView(budget = 2_300_123)
}