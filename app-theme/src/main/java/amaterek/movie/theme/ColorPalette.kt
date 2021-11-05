package amaterek.movie.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
@Stable
internal val DarkColorPalette = darkColors(
    background = AppColor.tmdbDarkBlue,
    onBackground = Color.LightGray,
    primary = AppColor.tmdbDarkBlue,
    onPrimary = Color.LightGray,
    surface = AppColor.tmdbDarkBlue,
    secondary = AppColor.tmdbLightBlue,
    error = AppColor.error,
)

@Stable
internal val LightColorPalette = lightColors(
    background = Color.White,
    onBackground = AppColor.tmdbDarkBlue,
    primary = AppColor.tmdbDarkBlue,
    onPrimary = Color.LightGray,
    surface = Color.White,
    secondary = AppColor.tmdbLightBlue,
    error = AppColor.error,
)