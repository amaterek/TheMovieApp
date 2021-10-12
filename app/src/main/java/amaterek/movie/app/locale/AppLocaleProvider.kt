package amaterek.movie.app.locale

import amaterek.base.locale.LocaleProvider
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLocaleProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : LocaleProvider {

    override fun getLocale(): Locale =
        if (Build.VERSION.SDK_INT >= 24) {
            context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
}