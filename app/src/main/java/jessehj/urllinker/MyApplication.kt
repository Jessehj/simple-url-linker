package jessehj.urllinker

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import jessehj.urllinker.manager.RealmManager
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        RealmManager.initializeRealm(applicationContext)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}