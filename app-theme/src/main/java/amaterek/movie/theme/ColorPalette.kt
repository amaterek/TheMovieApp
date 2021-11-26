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
    primary = AppColor.tmdbDarkBlue,
    onPrimary = Color.LightGray,
    surface = AppColor.tmdbDarkBlue,
    onSurface = Color.LightGray,
    secondary = AppColor.tmdbLightBlue,
    error = AppColor.error,
)

@Stable
internal val LightColorPalette = lightColors(
    background = Color.White,
    primary = AppColor.tmdbDarkBlue,
    onPrimary = Color.LightGray,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = AppColor.tmdbLightBlue,
    error = AppColor.error,
)