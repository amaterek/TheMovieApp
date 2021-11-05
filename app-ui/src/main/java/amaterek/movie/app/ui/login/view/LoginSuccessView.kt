package amaterek.movie.app.ui.login.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun LoginSuccessView() {
    Log.v("ComposeRender", "LoginSuccessScreen")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            Icons.Filled.Done,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
@Preview
private fun LoginSuccessPreview() {
    LoginSuccessView()
}
