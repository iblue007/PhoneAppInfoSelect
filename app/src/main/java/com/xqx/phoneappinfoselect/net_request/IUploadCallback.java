package com.xqx.phoneappinfoselect.net_request;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月18日 17:39.</br>
 * @update: </br>
 */

public interface IUploadCallback {

    void onUploadStart();

    void onNextSection(int num, int total);

    void onSectionCompleted(int num, int total);

    void onUploadFailed(int code);

    void onUploadCompleted();
}
