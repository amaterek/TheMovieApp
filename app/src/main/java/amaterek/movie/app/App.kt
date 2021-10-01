package amaterek.movie.app

import amaterek.base.log.AndroidLogger
import amaterek.base.log.Log
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    init {
        Log.setLogger(AndroidLogger())
    }
}
