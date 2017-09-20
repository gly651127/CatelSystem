package com.catel.network.http;

import com.catel.util.okhttp.CommonOkHttpClient;
import com.catel.util.okhttp.listener.DisposeDataHandle;
import com.catel.util.okhttp.listener.DisposeDataListener;
import com.catel.util.okhttp.listener.DisposeDownloadListener;
import com.catel.util.okhttp.request.CommonRequest;
import com.catel.util.okhttp.request.RequestParams;

/**
 * @author: vision
 * @function:
 * @date: 16/8/12
 */
public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener) {
        CommonOkHttpClient.get(CommonRequest.
                createPostRequest(url, params), new DisposeDataHandle(listener));
    }

    //根据参数发送所有get请求
    public static void getRequest(String url, RequestParams params, DisposeDataListener listener) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener));
    }

    /**
     * 获取树形
     *
     * @param listener
     */
//    public static void getAppTreeById(int appid, DisposeDataListener<WSResult<String>> listener) {
//
//        RequestParams params = new RequestParams();
//        params.put("id", appid + "");
//        RequestCenter.getRequest(HttpConstants.TREE_GET, params, listener);
//    }


    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }

}
