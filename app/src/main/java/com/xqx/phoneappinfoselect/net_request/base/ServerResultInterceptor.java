package com.xqx.phoneappinfoselect.net_request.base;

/**
 * @Description: 请求事件，可以将请求信息传进去，以便操作请求结果</br>
 * @author: cxy </br>
 * @date: 2017年07月17日 12:36.</br>
 * @update: </br>
 */

public abstract class ServerResultInterceptor {

    public abstract void onIntercept(int code);
}
