package com.xqx.phoneappinfoselect.net_request.upload;


import com.xqx.phoneappinfoselect.net_request.base.ResultCodeMap;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月18日 19:58.</br>
 * @update: </br>
 */

public class UploadResult {

    /**
     * 已上传的总段数
     */
    public int uploadedSectionCount;

    /**
     * 需上传的总段数
     */
    public int uploadTotal;
    /**
     * 结果码
     */
    public int resultCode = ResultCodeMap.SERVER_RESPONSE_CODE_SUCCESS;
}
