package com.cxc.common.http.httpbase.impl;

//public interface OnUrlChangeListener {
//}

import com.cxc.common.http.httpbase.ext.RetrofitUrlManager;

import okhttp3.HttpUrl;

/**
 * ================================================
 * Url 监听器
 * <p>
 * Created by JessYan on 20/07/2017 14:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface OnUrlChangeListener {

    /**
     * 此方法在框架使用 {@code domainName} 作为 key,从 {@link RetrofitUrlManager#mDomainNameHub}
     * 中取出对应的 BaseUrl 构建新的 Url 之前会被调用
     * <p>
     * 可以使用此回调确保 {@link RetrofitUrlManager#mDomainNameHub} 中是否已经存在自己期望的 BaseUrl
     * 如果不存在可以在此方法中进行 {@link RetrofitUrlManager#putDomain(String, String)}
     *
     * @param oldUrl
     * @param domainName
     */
    void onUrlChangeBefore(HttpUrl oldUrl, String domainName);

    /**
     * 当 Url 的 BaseUrl 被切换时回调
     * 调用时间是在接口请求服务器之前
     *
     * @param newUrl
     * @param oldUrl
     */
    void onUrlChanged(HttpUrl newUrl, HttpUrl oldUrl);
}

