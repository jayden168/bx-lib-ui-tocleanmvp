package co.bxvip.ui.tocleanmvp.base

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import android.util.DisplayMetrics

import com.qihoo360.replugin.RePlugin

import java.util.Locale

import java.sql.DriverManager.println


/**
 * @author jay
 * @date 2020/07/17
 * 多语言设置
 */
//object MultiLanguageUtils {
/**
 * 中文和英文标识
 */
val LANG_CN = "zh"
val LANG_EN = "en"
val TYPE_ID_CN = "1"
val TYPE_ID_EN = "2"

/**
 * 获取请求参数的类型ID
 * 1:中文 2:英文
 */
val languageTypeId: String
    get() = SPUtils.get(RePlugin.getHostContext(), "LANGUAGE_TYPE_ID", "") as String

/**
 * 判断是不是中文 根据typeid
 *
 * @return
 */
val isChinaFromTypeId: Boolean
    get() = languageTypeId == TYPE_ID_CN

/**
 * 判断是不是英文 根据typeid
 *
 * @return
 */
val isEnglishFromTypeId: Boolean
    get() = languageTypeId == TYPE_ID_EN

/**
 * 判断是不是中文
 *
 * @return
 */
val isChinaLang: Boolean
    get() {
        val locale = systemLanguage
        return locale === Locale.CHINA || locale === Locale.SIMPLIFIED_CHINESE || locale === Locale.CHINESE || locale.language == "zh"
    }

/**
 * 获取系统语言
 */
val systemLanguage: Locale
    get() {
        val locale: Locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0)
        } else {
            locale = Locale.getDefault()
        }
        return locale
    }

/**
 * 修改应用内语言
 *
 * @param language 语言  zh/en
 * @param area     地区
 */
fun changeLanguage(context: Context, language: String, area: String) {
    if (TextUtils.isEmpty(language) && TextUtils.isEmpty(area)) {
        //如果语言和地区都是空，那么跟随系统s
        SPUtils.put(context, SPUtils.SP_LANGUAGE, "")
        SPUtils.put(context, SPUtils.SP_COUNTRY, "")
    } else {
        //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
        val newLocale = Locale(language, area)
        changeAppLanguage(context, newLocale, true)
    }
}

/**
 * 使用用户设置的语言
 */
fun setUserLanguage(context: Context) {
    val language = SPUtils.get(context, SPUtils.SP_LANGUAGE, "") as String
    val country = SPUtils.get(context, SPUtils.SP_COUNTRY, "") as String
    val newLocale = Locale(language, country)
    changeAppLanguage(context, newLocale, true)
}

/**
 * 更改应用语言
 *
 * @param locale      语言地区
 * @param persistence 是否持久化
 */
fun changeAppLanguage(context: Context, locale: Locale, persistence: Boolean) {
    val resources = context.resources
    val metrics = resources.displayMetrics
    val configuration = resources.configuration
    setLanguage(context, locale, configuration)
    resources.updateConfiguration(configuration, metrics)
    if (persistence) {
        saveLanguageSetting(context, locale)
    }
}

/**
 * 设置语言
 *
 * @param context       上下文
 * @param locale        语言
 * @param configuration 配置对象
 */
private fun setLanguage(context: Context, locale: Locale, configuration: Configuration) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.setLocale(locale)
        configuration.locales = LocaleList(locale)
        context.createConfigurationContext(configuration)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        configuration.setLocale(locale)
    } else {
        configuration.locale = locale
    }
}

/**
 * 保存多语言信息到sp中
 */
fun saveLanguageSetting(context: Context, locale: Locale) {
    SPUtils.put(context, SPUtils.SP_LANGUAGE, locale.language)
    SPUtils.put(context, SPUtils.SP_COUNTRY, locale.country)
}

/**
 * 检查用户有没有设置过语言
 */
fun checkUserLanguage(context: Context): Boolean {
    val language = SPUtils.get(context, SPUtils.SP_LANGUAGE, "") as String
    val country = SPUtils.get(context, SPUtils.SP_COUNTRY, "") as String
    return !TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)
}

/**
 * 改变语言
 */
fun changeLanguage(context: Context) {
    //检查用户是否设置了语言
    if (checkUserLanguage(context)) {
        setUserLanguage(context)
    } else {
        //根据系统的语言来
        if (isChinaLang) {
            changeLanguage(context, "zh", "ZH")
        } else {
            changeLanguage(context, "en", "US")
        }
    }
}

/**
 * 初始化语言信息
 */
fun initLanguageInfo() {
    val language = SPUtils.get(RePlugin.getHostContext(), "LANGUAGE_TYPE_ID", "") as String
    //切换语言
    if (language.isNullOrEmpty()) {
        //默认切换至中文
        changeLanguage(RePlugin.getPluginContext(), LANG_CN, "ZH")
    } else if (language == TYPE_ID_CN) {
        //中文
        changeLanguage(RePlugin.getPluginContext(), LANG_CN, "ZH")
    } else if (language == TYPE_ID_EN) {
        //英文
        changeLanguage(RePlugin.getPluginContext(), LANG_EN, "US")
    }
}
//}
