package amaterek.movie.app.di

import amaterek.base.locale.LocaleProvider
import amaterek.movie.app.locale.AppLocaleProvider
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleModule {

    @Provides
    @Singleton
    fun provideLocaleProvider(@ApplicationContext context: Context): LocaleProvider =
        AppLocaleProvider(context)
}