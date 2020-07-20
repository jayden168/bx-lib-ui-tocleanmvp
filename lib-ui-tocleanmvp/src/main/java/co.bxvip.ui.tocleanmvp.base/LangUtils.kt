package co.bxvip.ui.tocleanmvp.base

import com.qihoo360.replugin.RePlugin

fun getStrRes(resId: Int): String {
    val context = RePlugin.getPluginContext()
    if (null != context) {
        return context.getString(resId)
    }
    return ""
}

fun getStrRes(resId: Int, vararg args: Any): String {
    val context = RePlugin.getPluginContext()
    if (null != context) {
        return context.getString(resId, *args)
    }
    return ""
}
