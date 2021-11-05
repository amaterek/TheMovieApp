package amaterek.movie.app.di

import amaterek.movie.app.usersession.AppUserSessionRepository
import amaterek.movie.domain.repository.UserSessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface UserSessionModule {

    @Binds
    fun bindUserSessionRepository(instance: AppUserSessionRepository): UserSessionRepository
}