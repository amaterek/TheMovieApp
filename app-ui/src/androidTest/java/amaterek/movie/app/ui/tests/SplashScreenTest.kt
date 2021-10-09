package amaterek.movie.app.ui.tests

import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.ComposeHiltTest
import amaterek.movie.app.ui.splash.SplashScreen
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@HiltAndroidTest
class SplashScreenTest : ComposeHiltTest() {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testSplashScreen() {
        composeTestRule.setContent {
            SplashScreen(onSplashFinished = {})
        }

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.tmdb_logo_content_description))
            .assertIsDisplayed()
    }
}
