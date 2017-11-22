/*
 *
 * Copyright 2017-2018 549477611@qq.com(xiaoyu)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.lakala.transaction.common.holder.httpclient;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpException;

import java.io.IOException;
import java.util.Map;

/**
 * 封装了HttpClient 往服务器发送请求的接口
 * 让用户 不适用任何HttpClient 的api 的
 * 直接调用该接口就可以实现相应的操作
 */
public interface HttpClientWrapper {
    /**
     * 设置协议头的信息 用户可以根据自己的需求而设定，否则使用默认的设置
     *
     * @param headers
     */

    void addHttpHeader(Map<String, String> headers);


    /**
     * 清除cookie信息
     */
     void clearCookie();

    /**
     * 把一组cookies加到 httpclient 中
     *
     * @param cookies
     */

     void addCookies(Cookie[] cookies);

    /**
     * 增加单个的cookie
     *
     * @param cookie
     */
     void addCookie(Cookie cookie);

    /**
     * @param method
     * @param url
     * @param params
     * @param charset
     * @return 返回带编码集的字符串
     * @throws HttpException
     * @throws IOException
     */
     String doRequest(MethodType method, String url,
                      Map<String, String> params, String charset) throws HttpException,
            IOException;

    /**
     * @param method
     * @param url
     * @param charset
     * @return 返回带编码集的结果
     * @throws HttpException
     * @throws IOException
     */
     String doRequest(MethodType method, String url, String charset)
            throws HttpException, IOException;

    /**
     * 无返回值 实现HttpResponseCallBack接口 对流进行处理封转返回值 外部可以利用到流来得到结果 主要考虑的是多线程下载的情况
     *
     * @param method
     * @param url
     * @param params
     * @param charset
     * @param is
     * @throws HttpException
     * @throws IOException
     */

    // HttpResponseCallBack 是设置的一个回调类 ，主要是考虑由于httpClient 返回的流 当连接关闭时流也关闭了
    // 所以利用回调的方式在流关闭之间嵌入用户代码，来操做流
     void doRequest(HttpResponseCallBack callback, MethodType method,
                    String url, Map<String, String> params, String charset)
            throws HttpException, IOException;

    /**
     * 无返回值 外部可以利用到流来得到结果 主要考虑的是多线程下载的情况
     *
     * @param method
     * @param url
     * @param charset
     * @param         　callback
     * @throws HttpException
     * @throws IOException
     */

    // HttpResponseCallBack 是设置的一个回调类 ，主要是考虑由于httpClient 返回的流 当连接关闭时流也关闭了
    // 所以利用回调的方式在流关闭之间嵌入用户代码，来操做流
    public void doRequest(HttpResponseCallBack callback, MethodType method,
                          String url, String charset) throws HttpException, IOException;

}
