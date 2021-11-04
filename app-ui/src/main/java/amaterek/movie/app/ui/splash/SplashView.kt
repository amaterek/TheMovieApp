package amaterek.movie.app.ui.splash

import amaterek.base.log.Log
import amaterek.movie.app.ui.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun SplashView(
    modifier: Modifier = Modifier,
    onRequestFinish: () -> Unit,
) {
    Log.v("ComposeRender", "SplashScreenView")

    Column(
        modifier = modifier
            .clickable(
                // no ripple effect
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { onRequestFinish() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.tmdb_logo),
            contentDescription = stringResource(R.string.tmdb_logo_content_description),
            contentScale = ContentScale.None
        )
    }
}

@Composable
@Preview
private fun SplashScreenPreview() {
    SplashView(
        onRequestFinish = {},
    )
}