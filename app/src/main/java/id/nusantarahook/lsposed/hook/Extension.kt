package id.nusantarahook.lsposed.hook

import android.app.Application
import android.content.Context
import android.widget.Toast
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

object BuildConfig {
    const val DEBUG = true
    const val VERSION_CODE = 1
    const val APPLICATION_ID = "id.nusantarahook.lsposed"
}

private var appContext: Context? = null

fun inContext(lpparam: LoadPackageParam, f: (Application) -> Unit) {
    XposedHelpers.findAndHookMethod(
        Application::class.java,
        "attach",
        Context::class.java,
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val app = param.thisObject as Application
                appContext = param.args[0] as Context
                Utils.init(param.args[0] as Context) // simpan context
                f(app) // callback ke MainHook
            }
        }
    )
}

fun showToast(message: String) {
    appContext?.let {
        Toast.makeText(it, message, Toast.LENGTH_LONG).show()
    } ?: XposedBridge.log("TOAST (no ctx): $message")
}

fun log(message: String) {
    if (!BuildConfig.DEBUG) return
    XposedBridge.log(message)
}

fun e(message: String) {
    XposedBridge.log(message)
}

object Logger {
    fun printDebug(message: () -> String) {
        XposedBridge.log("DEBUG: ${message()}")
    }

    fun printInfo(message: () -> String) {
        XposedBridge.log("INFO: ${message()}")
    }

    fun printError(message: () -> String) {
        XposedBridge.log("ERROR: ${message()}")
    }
}

object Utils {
    private var context: Context? = null

    fun init(ctx: Context) {
        context = ctx
    }

    fun showToastLong(text: String) {
        context?.let {
            Toast.makeText(it, text, Toast.LENGTH_LONG).show()
        } ?: XposedBridge.log("TOAST (no ctx): $text")
    }
}
