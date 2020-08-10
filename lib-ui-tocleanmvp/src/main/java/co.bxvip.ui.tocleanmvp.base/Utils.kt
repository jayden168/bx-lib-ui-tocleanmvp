package co.bxvip.ui.tocleanmvp.base

import co.bxvip.android.commonlib.utils.CommonInit
import java.io.File

object S3JsonFile {

    val GET_WAN_FA_LIST = "getWanfaShuoMingList_"
    val GET_WAN_FA_DETAILS = "getWanfaDetails_"

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