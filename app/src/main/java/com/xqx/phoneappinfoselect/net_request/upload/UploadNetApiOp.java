package com.xqx.phoneappinfoselect.net_request.upload;

import android.content.Context;

import com.xqx.phoneappinfoselect.NetLibUtil;
import com.xqx.phoneappinfoselect.UploadBean;
import com.xqx.phoneappinfoselect.net_request.base.BaseConfig;
import com.xqx.phoneappinfoselect.net_request.base.HttpCommon;
import com.xqx.phoneappinfoselect.net_request.base.HttpRequestParam;
import com.xqx.phoneappinfoselect.net_request.base.ServerResult;
import com.xqx.phoneappinfoselect.net_request.base.ServerResultHeader;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月16日 20:33.</br>
 * @update: </br>
 */

public class UploadNetApiOp {

    /**
     * 校验可用性（第一次握手）
     *
     * @param entireMd5 完整文件的MD5
     * @param name      名称
     * @param byteCount 完整文件大小
     * @param netType   当前网络类型
     * @param resType   资源类型
     * @param format    文件格式
     * @param desc      视频描述
     * @param tagIds    视频关联的标签ID列表
     * @param isOpen    是否公开
     * @param auditMode 审核模式。文件上传后的审核模式. 1-编辑审核,2-直接上架. 不传该参数默认为1-编辑审核
     * @param pFileId   父文件的ID值
     * @return
     */
//    public static final ServerResult<SectionBean> checkAvaliable(String entireMd5, String name, long byteCount, int netType,
//                                                                 int resType, String format, String desc, String tagIds, int isOpen, int auditMode, String pFileId) {
//        String jsonParams = "";
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("FileMd5", entireMd5);
//            jsonObject.put("Name", name);
//            jsonObject.put("TotalBytes", byteCount);
//            jsonObject.put("NetType", netType);
//            jsonObject.put("ResType", resType);
//            jsonObject.put("Format", format);
//            jsonObject.put("Desc", desc);
//
//            if (!TextUtils.isEmpty(pFileId)) {
//                jsonObject.put("ParentFileId", pFileId);
//            }
//
//            if (!TextUtils.isEmpty(tagIds)) {
//                jsonObject.put("TagIds", tagIds);
//            }
//
//            if (isOpen == 0 || isOpen == 1) {
//                jsonObject.put("IsOpen", isOpen);
//            }
//
//            if (auditMode == 1 || auditMode == 2) {
//                jsonObject.put("AuditMode", auditMode);
//            }
//
//            jsonParams = jsonObject.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        HashMap<String, String> paramsMap = new HashMap<String, String>();
//        HttpRequestParam.addGlobalLauncherRequestValue(paramsMap, jsonParams, LoginManager.getInstance().getSessionId());
//        HttpCommon httpCommon = new HttpCommon(getUploadUrl(7064));
//        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, jsonParams);
//        ServerResult<SectionBean> res = new ServerResult<SectionBean>();
//        if (csResult != null) {
//            res.setCsResult(csResult);
//            String responseStr = csResult.getResponseJson();
//            if (!TextUtils.isEmpty(responseStr)) {
//                try {
//                    JSONObject json = new JSONObject(responseStr);
//                    int len = json.optInt("SegSize");
//                    int count = json.optInt("TotalSec");
//                    // 轮询是否同步结束的时间间隔
//                    int askInterval = json.optInt("QueryInterval");
//                    String fileId = json.optString("FileId");
//                    if (count > 0 && len > 0) {
//                        SectionBean bean = new SectionBean();
//                        bean.total = count;
//                        bean.defLenght = len;
//                        bean.askInterval = askInterval;
//                        bean.fileId = fileId;
//                        res.itemList.add(bean);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return res;
//    }

    /**
     * 上传
     * @return
     */
    public static final String Request4TerminalID(Context context) {
        String TerminalID = null;
        String jsonParams = "";
        JSONObject jsonObject = new JSONObject();
        try {
            String  DivideVersion = NetLibUtil.utf8URLencode(NetLibUtil.getDivideVersion(context));
            String  SupPhone = NetLibUtil.utf8URLencode(NetLibUtil.getBuildMode());
            String  SupFirm = NetLibUtil.utf8URLencode(NetLibUtil.getBuildVersion());
            String  IMEI = NetLibUtil.utf8URLencode(NetLibUtil.getIMEI(context));
            String IMSI = NetLibUtil.utf8URLencode(NetLibUtil.getIMSI(context));
            String CUID = URLEncoder.encode(NetLibUtil.getCUID(context), "UTF-8");

            jsonObject.put("SupPhone", SupPhone);
            jsonObject.put("SupFirm", SupFirm);
            jsonObject.put("Platform", ""+4);
            jsonObject.put("Channel", "yyb");
            jsonObject.put("DivideVersion", DivideVersion);
            jsonObject.put("Duid", CUID);//通用用户唯一标识 NdAnalytics.getCUID(ctx)
            jsonObject.put("Pid", BaseConfig.APPID + "");
            jsonObject.put("Imei", IMEI);
            jsonObject.put("Imsi", IMSI);

            jsonParams = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        HttpRequestParam.addGlobalLauncherRequestValue(paramsMap, jsonParams,"");
        HttpCommon httpCommon = new HttpCommon(getUploadUrl4TerminalId());
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, jsonParams);
        ServerResult res = new ServerResult();
        if (csResult != null) {
            res.setCsResult(csResult);
            String result = csResult.getResponseJson();
            int resultCode = csResult.getResultCode();
            if(resultCode == 0){
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    TerminalID = jsonObj.optString("TerminalID");
                    return TerminalID;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return TerminalID;
    }

    /**
     * 上传
     *
     * @param base64Data 当前段的byte MD5
     * @return
     */
    public static final ServerResult upload(String base64Data, String FirmVersion, String Brand,String PhoneModel, String PhoneOS,String resolution,float density,String defaultLaout,String terminalID) {
        String jsonParams = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DeLayoutSpec", defaultLaout);
            jsonObject.put("ResolutionRatio", resolution);
            jsonObject.put("ScreenDensity", density);
            jsonObject.put("FirmVersion", FirmVersion);
            jsonObject.put("Brand", Brand);
            jsonObject.put("PhoneOS", PhoneOS);
            jsonObject.put("PhoneModel", PhoneModel);
            jsonObject.put("Data", base64Data);
            jsonParams = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        HttpRequestParam.addGlobalLauncherRequestValue(paramsMap, jsonParams,terminalID);
        HttpCommon httpCommon = new HttpCommon(getUploadUrl4Upload());
        ServerResultHeader csResult = httpCommon.getResponseAsCsResultPost(paramsMap, jsonParams);
        ServerResult<UploadBean> commonListResult = new ServerResult();
        if (csResult != null) {
            commonListResult.setCsResult(csResult);
            if(commonListResult.getCsResult().isRequestOK()){
                ArrayList<UploadBean> resultList = new ArrayList<>();
                String result = commonListResult.getCsResult().getResponseJson();
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    boolean success = jsonObj.optBoolean("Success");
                    String msg = jsonObj.optString("Msg");
                    csResult.setResultMessage(msg);
                    UploadBean uploadBean = new UploadBean();
                    uploadBean.setSuccess(success);
                    uploadBean.setMsg(msg);
                    resultList.add(uploadBean);
                    commonListResult.itemList = resultList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return commonListResult;
    }

    private static String getUploadUrl4Upload() {
        return "http://stat.ifjing.com/action/commonaction/"+12;
    }
    private static String getUploadUrl4TerminalId() {
        return "http://stat.ifjing.com/action/commonaction/"+7;
    }
}
