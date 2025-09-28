package id.nusantarahook.lsposed.hook.app

import android.app.Activity
import android.app.AndroidAppHelper
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import id.nusantarahook.lsposed.hook.BuildConfig

class TelegramSpeed : IXposedHookLoadPackage {
    override fun handleLoadPackage(loadPackageParam: LoadPackageParam) {
        if (BuildConfig.APPLICATION_ID == loadPackageParam.packageName || !loadPackageParam.isFirstApplication) {
            return
        }

        try {
            XposedHelpers.findAndHookMethod(
                "org.telegram.messenger.FileLoadOperation",
                loadPackageParam.classLoader,
                "updateParams",
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val downloadChunkSizeBig: Int
                        val maxDownloadRequests: Int
                        val maxDownloadRequestsBig: Int
                        val speed: String = BOOST_EXTREME
                        if (BOOST_AVERAGE == speed) {
                            downloadChunkSizeBig = 1024 * 512
                            maxDownloadRequests = 8
                            maxDownloadRequestsBig = 8
                        } else if (BOOST_EXTREME == speed) {
                            downloadChunkSizeBig = 1024 * 1024
                            maxDownloadRequests = 12
                            maxDownloadRequestsBig = 12
                        } else {
                            downloadChunkSizeBig = 1024 * 128
                            maxDownloadRequests = 4
                            maxDownloadRequestsBig = 4
                        }
                        val maxCdnParts = (DEFAULT_MAX_FILE_SIZE / downloadChunkSizeBig).toInt()
                        XposedHelpers.setIntField(
                            param.thisObject,
                            "downloadChunkSizeBig",
                            downloadChunkSizeBig
                        )
                        XposedHelpers.setObjectField(
                            param.thisObject,
                            "maxDownloadRequests",
                            maxDownloadRequests
                        )
                        XposedHelpers.setObjectField(
                            param.thisObject,
                            "maxDownloadRequestsBig",
                            maxDownloadRequestsBig
                        )
                        XposedHelpers.setObjectField(param.thisObject, "maxCdnParts", maxCdnParts)

                        if (BOOST_NONE != speed) {
                            try {
                                val fileSize =
                                    XposedHelpers.getLongField(param.thisObject, "totalBytesCount")
                                if (fileSize > 15 * 1024 * 1024 && System.currentTimeMillis() - speedUpShown > 1000 * 60 * 5) {
                                    speedUpShown = System.currentTimeMillis()
                                    val speedString: String
                                    if (BOOST_AVERAGE == speed) {
                                        speedString = "BOOST_AVERAGE"
                                    } else if (BOOST_EXTREME == speed) {
                                        speedString = "BOOST_EXTREME"
                                    } else {
                                        speedString = "BOOST_NONE"
                                    }
                                    val title = "BOST ACTIVE"
                                    val subtitle = "🤮" + speedString + " Active 🤮"
                                    try {
                                        val notificationCenterClass = XposedHelpers.findClass(
                                            "org.telegram.messenger.NotificationCenter",
                                            loadPackageParam.classLoader
                                        )
                                        val globalInstance = XposedHelpers.callStaticMethod(
                                            notificationCenterClass,
                                            "getGlobalInstance"
                                        )
                                        Handler(Looper.getMainLooper()).post(Runnable {
                                            XposedHelpers.callMethod(
                                                globalInstance,
                                                "postNotificationName",
                                                XposedHelpers.getStaticIntField(
                                                    notificationCenterClass,
                                                    "showBulletin"
                                                ),
                                                arrayOf<Any?>(
                                                    TYPE_ERROR_SUBTITLE,
                                                    title,
                                                    subtitle
                                                )
                                            )
                                        })
                                    } catch (t: Throwable) {
                                        XposedBridge.log(t)
                                        Toast.makeText(
                                            AndroidAppHelper.currentApplication(),
                                            title + "\n" + subtitle,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (t: Throwable) {
                                XposedBridge.log(t)
                            }
                        }
                    }
                })
        } catch (t: Throwable) {
            XposedBridge.log(t)
            XposedHelpers.findAndHookMethod(
                Activity::class.java,
                "onResume",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        Toast.makeText(
                            param.thisObject as Activity?,
                            "TeleSpeed: " + "UNSUPPORT",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }

        try {
            XposedHelpers.findAndHookMethod(
                Application::class.java,
                "attach",
                Context::class.java,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        param.thisObject as Application?
                        param.args[0] as Context?

                        //Toast.makeText((Context) context, "TeleSpeed: " + "ACTIVE 🤮🤮", Toast.LENGTH_LONG).show();
                        try {
                            val notificationCenterClass = XposedHelpers.findClass(
                                "org.telegram.messenger.NotificationCenter",
                                loadPackageParam.classLoader
                            )
                            val globalInstance = XposedHelpers.callStaticMethod(
                                notificationCenterClass,
                                "getGlobalInstance"
                            )
                            Handler(Looper.getMainLooper()).post(Runnable {
                                XposedHelpers.callMethod(
                                    globalInstance,
                                    "postNotificationName",
                                    XposedHelpers.getStaticIntField(
                                        notificationCenterClass,
                                        "showBulletin"
                                    ),
                                    arrayOf<Any>(
                                        6,
                                        "🪷 Telegram BOST_EXTREME Active 🪷"
                                    )
                                )
                            })
                        } catch (t: Throwable) {
                            XposedBridge.log(t)
                        }
                    }
                })
        } catch (t: Throwable) {
            XposedBridge.log(t)
        }
    }

    companion object {
        private const val TAG = "SpeedHook"
        private const val KEY_SPEED = "speed"
        const val BOOST_NONE: String = "none"
        const val BOOST_AVERAGE: String = "average"
        const val BOOST_EXTREME: String = "extreme"
        private const val TYPE_ERROR_SUBTITLE = 4
        private val DEFAULT_MAX_FILE_SIZE = 1024L * 1024L * 2000L
        private var speedUpShown: Long = 0
    }
}
