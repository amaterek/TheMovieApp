package amaterek.movie.app.ui.login.view

import amaterek.movie.theme.view.AppTextButton
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun LoginErrorView(onTryAgain: () -> Unit) {
    Log.v("ComposeRender", "LoginErrorScreen")

    BackHandler(onBack = onTryAgain)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            Icons.Filled.Error,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .align(Alignment.Center),
        )

        AppTextButton(
            text = "Try again",
            onClick = onTryAgain,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
@Preview
private fun LoginErrorPreview() {
    LoginErrorView(
        onTryAgain = {},
    )
}

