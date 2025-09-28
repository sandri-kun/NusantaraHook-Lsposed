package id.nusantarahook.lsposed.common

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

// =============================================================================
// NUMERIC EXTENSIONS
// =============================================================================

fun Long.toFormattedSize(): String {
    if (this <= 0) return "0 B"
    val units = arrayOf("B", "KiB", "MiB", "GiB", "TiB")
    val digitGroups = (log10(this.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(
        this / 1024.0.pow(digitGroups.toDouble())
    ) + " " + units[digitGroups]
}

fun Long.toFormattedTime(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return if (hours > 0) {
        "%02d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%02d:%02d".format(minutes, seconds)
    }
}
