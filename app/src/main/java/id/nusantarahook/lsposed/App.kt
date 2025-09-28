package id.nusantarahook.lsposed

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import id.nusantarahook.lsposed.screen.debug.DebugActivity
import kotlin.system.exitProcess

class App : Application() {
    private var uncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null

    companion object {
        lateinit var appContext: Context

        val globalClass
            get() = appContext as App
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val intent = Intent(applicationContext, DebugActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("error", Log.getStackTraceString(throwable))
            startActivity(intent)
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }
}