package co.bxvip.ui.tocleanmvp.base

import android.content.pm.PackageManager
import co.bxvip.android.commonlib.utils.CommonInit
import co.bxvip.android.commonlib.utils.Preference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.ArrayList

object JsonFile {

    val GET_WAN_FA_LIST = "getWanfaShuoMingList_"
    val GET_WAN_FA_DETAILS = "getWanfaDetails_"
    val GET_TRAD_TYPE_DETAILS = "getTradType_"

    fun getFile(fileName: String): File = File(CommonInit.ctx.externalCacheDir.absolutePath + "sp_dir", "$fileName.txt")
    fun writeJson(json: String, fileName: String) {
        try {
            val f = getFile(fileName)
            f.parentFile.mkdirs()
            f.createNewFile()
            f.writeText(json)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun readJson(fileName: String): String {
        val f = getFile(fileName)
        return if (!f.exists()) {
            ""
        } else {
            f.readText()
        }
    }
}

object MateUtils {
    fun getMate(key: String): String {
        var c = ""
        try {
            val packageManager = CommonInit.ctx.packageManager
            if (packageManager != null) {
                val applicationInfo = packageManager.getApplicationInfo(CommonInit.ctx.packageName, PackageManager.GET_META_DATA)
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        c = applicationInfo.metaData.getString(key) ?: ""
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return c
    }
}