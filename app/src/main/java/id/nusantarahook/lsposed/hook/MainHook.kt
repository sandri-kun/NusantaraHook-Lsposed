package id.nusantarahook.lsposed.hook

import androidx.annotation.Keep
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import id.nusantarahook.lsposed.hook.app.AnimePlay
import id.nusantarahook.lsposed.hook.app.AutoClicker
import id.nusantarahook.lsposed.hook.app.CodeStudio
import id.nusantarahook.lsposed.hook.app.DeveloperAssistant
import id.nusantarahook.lsposed.hook.app.Donghub
import id.nusantarahook.lsposed.hook.app.FileManager
import id.nusantarahook.lsposed.hook.app.GooglePhotos
import id.nusantarahook.lsposed.hook.app.MusicSpeed
import id.nusantarahook.lsposed.hook.app.SecureVpn
import id.nusantarahook.lsposed.hook.app.TelegramSpeed
import id.nusantarahook.lsposed.hook.app.ThunderVpn
import id.nusantarahook.lsposed.hook.app.Warp
import id.nusantarahook.lsposed.hook.app.Wps

@Keep
class MainHook : IXposedHookLoadPackage {

    companion object {
        init {
            try {
                System.loadLibrary("dexkit")
                XposedBridge.log("Succes load dexkit")
            } catch (e: UnsatisfiedLinkError) {
                XposedBridge.log("MainHook: native lib 'DEXKIT' failed to load: ${e.message}")
            } catch (t: Throwable) {
                XposedBridge.log("MainHook: unexpected error while loading native lib: ${t.message}")
            }
        }
    }

    var targetPackageName: String? = null
    val hooksByPackage = mapOf(
        "dev.animeplay.app" to AnimePlay(),
        "com.fast.free.unblock.thunder.vpn" to ThunderVpn(),
        "com.fast.free.unblock.secure.vpn" to SecureVpn(),
        "org.telegram.messenger" to TelegramSpeed(),
        "cn.wps.moffice_eng" to Wps(),
        "com.alphainventor.filemanager" to FileManager(),
        "com.alif.ide" to CodeStudio(),
        "com.anichin.donghub" to Donghub(),
        "com.smp.musicspeed" to MusicSpeed(),
        "com.cloudflare.onedotonedotonedotone" to Warp(),
        "com.truedevelopersstudio.automatictap.autoclicker" to AutoClicker(),
        "com.appsisle.developerassistant" to DeveloperAssistant(),
        "com.google.android.apps.photos" to GooglePhotos()
    )

    fun shouldHook(packageName: String): Boolean {
        if (!hooksByPackage.containsKey(packageName)) return false
        if (targetPackageName == null) targetPackageName = packageName
        return targetPackageName == packageName
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (!lpparam.isFirstApplication) return
        if (!shouldHook(lpparam.packageName)) return

        hooksByPackage[lpparam.packageName]?.handleLoadPackage(lpparam)
    }
}
