package id.nusantarahook.lsposed.hook.app

import android.os.Build
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import id.nusantarahook.lsposed.hook.BaseHook
import org.luckypray.dexkit.wrap.DexMethod

class GooglePhotos : BaseHook() {

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        if (lpparam.packageName != "com.google.android.apps.photos") return

        // wajib di panggil untuk baseHook
        this.classLoader = lpparam.classLoader

        // 1. Override info Build
        val buildInfo = mapOf(
            "BRAND" to "google",
            "MANUFACTURER" to "Google",
            "DEVICE" to "marlin",
            "PRODUCT" to "marlin",
            "MODEL" to "Pixel XL",
            "FINGERPRINT" to "google/marlin/marlin:10/QP1A.191005.007.A3/5972272:user/release-keys"
        )
        val buildClazz = Build::class.java
        for ((k, v) in buildInfo) {
            XposedHelpers.setStaticObjectField(buildClazz, k, v)
        }

        // 2. Atur feature flags
        val featuresToEnable = setOf(
            "com.google.android.apps.photos.NEXUS_PRELOAD",
            "com.google.android.apps.photos.nexus_preload",
        )

        val featuresToDisable = setOf(
            "com.google.android.apps.photos.PIXEL_2017_PRELOAD",
            "com.google.android.apps.photos.PIXEL_2018_PRELOAD",
            "com.google.android.apps.photos.PIXEL_2019_MIDYEAR_PRELOAD",
            "com.google.android.apps.photos.PIXEL_2019_PRELOAD",
            "com.google.android.feature.PIXEL_2020_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2020_EXPERIENCE",
            "com.google.android.feature.PIXEL_2021_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2021_EXPERIENCE",
            "com.google.android.feature.PIXEL_2022_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2022_EXPERIENCE",
            "com.google.android.feature.PIXEL_2023_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2023_EXPERIENCE",
            "com.google.android.feature.PIXEL_2024_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2024_EXPERIENCE",
            "com.google.android.feature.PIXEL_2025_MIDYEAR_EXPERIENCE",
        )

        // 3. Hook hasSystemFeature
        val hook = object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val feature = param.args[0] as String
                param.result = when (feature) {
                    in featuresToEnable -> true
                    in featuresToDisable -> false
                    else -> return
                }
            }
        }

        DexMethod("Landroid/app/ApplicationPackageManager;->hasSystemFeature(Ljava/lang/String;)Z")
            .hookMethod(hook)

        DexMethod("Landroid/app/ApplicationPackageManager;->hasSystemFeature(Ljava/lang/String;I)Z")
            .hookMethod(hook)
    }
}
