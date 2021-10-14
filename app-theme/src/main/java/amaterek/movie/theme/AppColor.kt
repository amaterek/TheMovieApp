package amaterek.movie.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Immutable
object AppColor {
    @Stable val tmdbDarkBlue = Color(0xFF032541)
    @Stable val tmdbLightBlue = Color(0xFF01B4E4)
    @Stable val tmdbLighterGreen = Color(0xFFC0FECF)
    @Stable val error = Color(217,59,99)

    @Stable val ratingStarEnabled = Color(0xFFFFBF00)
    @Stable val ratingStarDisabled= Color(0xFFC0C0C0)
}