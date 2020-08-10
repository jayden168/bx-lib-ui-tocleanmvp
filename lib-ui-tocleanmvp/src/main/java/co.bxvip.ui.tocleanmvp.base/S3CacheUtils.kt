package co.bxvip.ui.tocleanmvp.base

import android.content.Context
import com.qihoo360.replugin.RePlugin

/**
 * @Author: pay001
 * @Date: 2020/8/10
 * @Desc:s3环境操作工具类
 */

var WAN_FA_CONFIG_TIME_TAG = "Wan_fa_Config_Time"

//玩法列表和详情的接口
var URL_GET_WANFA_CONFIG = getUploadUrl() + "/cpglobal/cp_play_config/update.json"
var URL_GET_WANFA_DETAILS = getUploadUrl() + "/cpglobal/cp_play_config/"
var URL_FU = "-"
var URL_File_Name = ".json"

fun getWanFaDetailsUrl(js_tag: String): String {
    return URL_GET_WANFA_DETAILS + js_tag + URL_FU + getLanguageType() + URL_File_Name
}

/**
 * 获取当前语言类型
 * @return 1:中文 2:英文
 */
fun getLanguageType(): String {
    return SPUtils.get(RePlugin.getHostContext(), "LANGUAGE_TYPE_ID", "") as String
}

/**
 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
 *
 * @param context
 * @param key
 * @param defaultObject
 * @return
 */
fun get(context: Context, key: String, defaultObject: Any): Any? {
    val sp = context.getSharedPreferences("language_share_data", Context.MODE_PRIVATE)

    if (defaultObject is String) {
        return sp.getString(key, defaultObject)
    } else if (defaultObject is Int) {
        return sp.getInt(key, defaultObject)
    } else if (defaultObject is Boolean) {
        return sp.getBoolean(key, defaultObject)
    } else if (defaultObject is Float) {
        return sp.getFloat(key, defaultObject)
    } else if (defaultObject is Long) {
        return sp.getLong(key, defaultObject)
    }

    return null
}

/**
 * 工具js_tag获取对应的玩法配置。
 */
fun getWanfaConfig(configs: MutableList<WanfaConfig>, js_tag: String, lang_type: String): WanfaConfig? {
    try {
        return configs.filter {
            it.js_tag == js_tag && it.lang_type == lang_type
        }[0]
    } catch (e: Exception) {
    }
    return null
}

/**
 * 将数据转换成配置列表
 */
fun getWanfaConfigFromData(data: String, js_tag: String, lang_type: String): WanfaConfig? {
    try {
        if (!data.isNullOrEmpty()) {
            return getWanfaConfig(Gson().fromJson<MutableList<WanfaConfig>>(data,
                    object : TypeToken<MutableList<WanfaConfig>>() {}.type), js_tag, lang_type)
        }
    } catch (e: Exception) {
    }
    return null
}

/**
 * 将数据转换成配置列表
 */
fun getWanfaDetailsFromData(data: String): WanfaDetails? {
    try {
        if (!data.isNullOrEmpty()) {
            return Gson().fromJson<WanfaDetails>(data,
                    object : TypeToken<WanfaDetails>() {}.type)
        }
    } catch (e: Exception) {
    }
    return null
}

/***
 * 检查玩法详情配置
 */
fun checkWanfaDetailsIsValid(up_time: Long, js_tag: String): WanfaDetails? {
    val cacheJson = S3JsonFile.readJson(S3JsonFile.GET_WAN_FA_DETAILS + getLanguageType() + "_$js_tag")
    var details: WanfaDetails? = null
    if (!cacheJson.isNullOrEmpty()) {
        details = getWanfaDetailsFromData(cacheJson) ?: return null
        //检验数据是否过期
        if (up_time != details.ver) {
            return null
        }
    }
    return details
}

/**
 * 检查玩法配置是否有效
 * 1.时间超过半小时
 * @return config返回null 表示验证失败，需要重新获取数据
 */
fun checkWanfaConfigIsValid(endTime: Long, currTime: Long, js_tag: String, context: Context): WanfaConfig? {
    try {
        //判断是否超时
        if (isOverstepMovableSort(endTime, currTime)) {
            return null
        }
        //没超过半小时限制 从缓存中去取
        val cacheJson = S3JsonFile.readJson(S3JsonFile.GET_WAN_FA_LIST)
        if (!cacheJson.isNullOrEmpty()) {
            return getWanfaConfigFromData(cacheJson, js_tag, getLanguageType())
        }
    } catch (e: Exception) {
    }
    return null
}

/**
 * 30分钟限制
 *
 * @return
 */
fun isOverstepMovableSort(endTime: Long, currTime: Long): Boolean {
    if (endTime == 0L || currTime == 0L) {
        return false
    }
    val tempTime = currTime - endTime
    if (tempTime <= 0) {
        return false
    }
    //只有大于等于两小时才算合格
    //        if (tempTime >= (1000 * 60 * 2)) { //测试时2分钟
    return tempTime >= 1000 * 60 * 30
}

fun getUploadUrl(): String {
    return getSystemInfo("upload_url")
}

private fun getSystemInfo(key: String): String {
    return LoginUtil.getCacheValue("System-$key")
}
