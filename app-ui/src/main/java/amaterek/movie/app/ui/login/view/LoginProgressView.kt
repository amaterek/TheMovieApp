package amaterek.movie.app.ui.login.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun LoginProgressView() {
    Log.v("ComposeRender", "LoginProgressScreen")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
@Preview
private fun LoginProgressPreview() {
    LoginProgressView()
}
