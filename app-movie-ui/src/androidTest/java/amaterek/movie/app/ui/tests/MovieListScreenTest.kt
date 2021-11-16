package amaterek.movie.app.ui.tests

import amaterek.movie.app.ui.R
import amaterek.movie.app.ui.common.ComposeHiltTest
import amaterek.movie.app.ui.movielist.MovieListScreen
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@HiltAndroidTest
class MovieListScreenTest : ComposeHiltTest() {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testMovieListScreen() {
        composeTestRule.setContent {
            MovieListScreen(onMovieClick = {})
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_name))
            .assertIsDisplayed()

        // TODO Write movie list tests
    }
}
