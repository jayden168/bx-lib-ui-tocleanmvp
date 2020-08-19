package co.bxvip.ui.tocleanmvp.base

/**
 * @Author: pay001
 * @Date: 2020/7/13
 * @Desc: 新配置玩法说明列表
 */

// 玩法对应的js_tag 语言类型 更新时间
class WanfaConfig(var js_tag: String = "", var lang_type: String = "1", var up_time: String = "")

class WanfaDetails(var menu_list: MutableList<MenuItem>, var play_list: MutableList<PlayItem>, var ver: Long = 0)

//菜单条目
class MenuItem(var menuid: Int = 0, var name: String = "")

//玩法详情
class PlayItem(var content: String = "", var play_fanli: String = "", var play_shuoming: String = "", var play_title: String = "",
               var play_type: String = "", var playid: Int = 0, var playname: String = "", var wanfa: String = "")

//菜单条目
class S3TradType(var data: MutableList<TradTypeItem>, var id: String = "", var name: String = "")

//玩法详情
class TradTypeItem(var id: String = "", var name: String = "")