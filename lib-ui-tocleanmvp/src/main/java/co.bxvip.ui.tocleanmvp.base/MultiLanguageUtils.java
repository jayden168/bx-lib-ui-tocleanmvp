package co.bxvip.ui.tocleanmvp.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.qihoo360.replugin.RePlugin;

import java.util.Locale;

import static java.sql.DriverManager.println;


/**
 * @author jay
 * @date 2020/07/17
 * 多语言设置
 */
public class MultiLanguageUtils {
    /**
     * 中文和英文标识
     */
    public static final String LANG_CN = "zh";
    public static final String LANG_EN = "en";
    public static final String TYPE_ID_CN = "1";
    public static final String TYPE_ID_EN = "2";

    /**
     * 修改应用内语言
     *
     * @param language 语言  zh/en
     * @param area     地区
     */
    public static void changeLanguage(Context context, String language, String area) {
        if (TextUtils.isEmpty(language) && TextUtils.isEmpty(area)) {
            //如果语言和地区都是空，那么跟随系统s
            SPUtils.put(context, SPUtils.SP_LANGUAGE, "");
            SPUtils.put(context, SPUtils.SP_COUNTRY, "");
        } else {
            //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
            Locale newLocale = new Locale(language, area);
            changeAppLanguage(context, newLocale, true);
        }
    }

    /**
     * 使用用户设置的语言
     */
    public static void setUserLanguage(Context context) {
        String language = (String) SPUtils.get(context, SPUtils.SP_LANGUAGE, "");
        String country = (String) SPUtils.get(context, SPUtils.SP_COUNTRY, "");
        Locale newLocale = new Locale(language, country);
        changeAppLanguage(context, newLocale, true);
    }

    /**
     * 获取请求参数的类型ID
     * 1:中文 2:英文
     */
    public static String getLanguageTypeId(Context context) {
        String language = (String) SPUtils.get(context, SPUtils.SP_LANGUAGE, "");
        //检查用户是否设置了语言
        if (TextUtils.isEmpty(language)) {
            //根据系统的语言来
            return isChinaLang() ? TYPE_ID_CN : TYPE_ID_EN;
        }
        return language.equals(LANG_CN) ? TYPE_ID_CN : TYPE_ID_EN;
    }

    /**
     * 更改应用语言
     *
     * @param locale      语言地区
     * @param persistence 是否持久化
     */
    public static void changeAppLanguage(Context context, Locale locale, boolean persistence) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        setLanguage(context, locale, configuration);
        resources.updateConfiguration(configuration, metrics);
        if (persistence) {
            saveLanguageSetting(context, locale);
        }
    }

    /**
     * 设置语言
     *
     * @param context       上下文
     * @param locale        语言
     * @param configuration 配置对象
     */
    private static void setLanguage(Context context, Locale locale, Configuration configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);
            configuration.setLocales(new LocaleList(locale));
            context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
    }

    /**
     * 保存多语言信息到sp中
     */
    public static void saveLanguageSetting(Context context, Locale locale) {
        SPUtils.put(context, SPUtils.SP_LANGUAGE, locale.getLanguage());
        SPUtils.put(context, SPUtils.SP_COUNTRY, locale.getCountry());
    }

    /**
     * 检查用户有没有设置过语言
     */
    public static boolean checkUserLanguage(Context context) {
        String language = (String) SPUtils.get(context, SPUtils.SP_LANGUAGE, "");
        String country = (String) SPUtils.get(context, SPUtils.SP_COUNTRY, "");
        return !TextUtils.isEmpty(language) && !TextUtils.isEmpty(country);
    }

    /**
     * 判断是不是中文
     *
     * @return
     */
    public static boolean isChinaLang() {
        Locale locale = getSystemLanguage();
        return (locale == Locale.CHINA || locale == Locale.SIMPLIFIED_CHINESE || locale == Locale.CHINESE || locale.getLanguage().equals("zh"));
    }

    /**
     * 获取系统语言
     */
    public static Locale getSystemLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 改变语言
     */
    public static void changeLanguage(Context context) {
        //检查用户是否设置了语言
        if (checkUserLanguage(context)) {
            setUserLanguage(context);
        } else {
            //根据系统的语言来
            if (isChinaLang()) {
                MultiLanguageUtils.changeLanguage(context, "zh", "ZH");
            } else {
                MultiLanguageUtils.changeLanguage(context, "en", "US");
            }
        }
    }

    /**
     * 初始化语言信息
     */
    public static void initLanguageInfo() {
        String language = (String) SPUtils.get(RePlugin.getHostContext(), "LANGUAGE_TYPE_ID", "");
        //切换语言
        if (language == TYPE_ID_CN) {
            //中文
            MultiLanguageUtils.changeLanguage(RePlugin.getPluginContext(), LANG_CN, "ZH");
        } else if (language == TYPE_ID_EN) {
            //英文
            MultiLanguageUtils.changeLanguage(RePlugin.getPluginContext(), LANG_EN, "US");
        }
    }
}
