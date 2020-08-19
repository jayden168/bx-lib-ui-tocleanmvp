package co.bxvip.ui.tocleanmvp.base

import okhttp3.*

/**
 * @Author: pay001
 * @Date: 2020/7/14
 * @Desc: 独立的网络请求类 用于请求Json文件数据
 */

fun httpReq(url: String, callback: Callback) {
    val client = OkHttpClient()
    //构造Request对象
    //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
    val request = Request.Builder().get().url(url).build()
    val call = client.newCall(request)
    call.enqueue(callback)
}