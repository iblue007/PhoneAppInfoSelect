package com.xqx.phoneappinfoselect.net_request.upload;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月16日 20:12.</br>
 * @update: </br>
 */
public class UploadUtil {

    public static final int ERROR_CODE_NAME_EMPTY = -20;
    public static final int ERROR_CODE_DURATION_TOO_LONG = -21;
    public static final int ERROR_CODE_FILE_TOO_LARGE = -22;
    public static final int ERROR_CODE_MIMETYPE_UNSUPPORTED = -23;
    public static final int ERROR_CODE_IS_NOT_PORT = -24;//非竖屏


//    public static UploadResult upload(String name, String data, String thumb, String desc, String tagIds, int isOpen, long maxTime, IUploadCallback callback) {
//        UploadResult result = new UploadResult();
//        if ((result.resultCode = verify(name, data, maxTime)) != ResultCodeMap.SERVER_RESPONSE_CODE_SUCCESS) {
//            if (callback != null) {
//                callback.onUploadFailed(result.resultCode);
//            }
//            return result;
//        }
//        File file = new File(data);
//        String entireMd5 = DigestUtil.getFileMD5(file);
//        try {
//            if (callback != null) {
//                callback.onUploadStart();
//            }
//            long byteCount = file.length();
//            int netType = TelephoneUtil.isWifiEnable(Global.getContext()) ? 1 : 2;
//            int resType = 71001;
//            String format = "mp4";
//
//            //检查本地已上传，但中途失败的文件上传记录
//            SectionBean info = ConfigPreferences.getInstance().getVideoUploadLogging(entireMd5);
//            if (info == null) {
//                /**
//                 * @param entireMd5 完整文件的MD5
//                 * @param name      名称
//                 * @param byteCount 完整文件大小
//                 * @param netType   当前网络类型
//                 * @param resType   资源类型
//                 * @param format    文件格式
//                 * @param desc      视频描述
//                 * @param tagIds    视频关联的标签ID列表
//                 * @param isOpen    是否公开
//                 * @param auditMode 审核模式。文件上传后的审核模式. 1-编辑审核,2-直接上架. 不传该参数默认为1-编辑审核
//                 * @param pMd5      父文件的MD5值
//                 */
//                //请求握手信息
//                ServerResult<SectionBean> resAvaliable = UploadNetApiOp.checkAvaliable(entireMd5, name, byteCount, netType, resType, format, desc, tagIds, isOpen, 2, null);
//                if (resAvaliable != null) {
//                    result.resultCode = resAvaliable.getCsResult().getResultCode();
//                    if (resAvaliable.getCsResult().isRequestOK()) {
//                        if (resAvaliable.itemList != null && !resAvaliable.itemList.isEmpty()) {
//                            info = resAvaliable.itemList.get(0);
//                        }
//                    }
//                } else {
//                    result.resultCode = ResultCodeMap.RESULT_CODE_EXCEPTION;
//                }
//            }
//
//            if (info != null) {
//                //先上传封面
//                uploadThumb(name, info.fileId, thumb, desc, tagIds, isOpen);
//                //开始上传
//                RandomAccessFile randomAccessFile = new RandomAccessFile(data, "rw");
//                for (int i = info.num, len = info.total; i < len; i++) {
//                    SectionBean section = new SectionBean();
//                    section.total = info.total;
//                    section.num = i;//从0开始
//                    section.defLenght = info.defLenght;
//                    section.entireMd5 = entireMd5;
//                    section.fileId = info.fileId;
//                    if (callback != null) {
//                        callback.onNextSection(i + 1, info.total);
//                    }
//                    ServerResult secRes = uploadSection(section, randomAccessFile, resType);
//                    if (secRes == null) {
//                        //异常
//                        result.resultCode = ResultCodeMap.RESULT_CODE_EXCEPTION;
//                        break;
//                    } else {
//                        result.resultCode = secRes.getCsResult().getResultCode();
//                        //服务端响应未成功
//                        if (result.resultCode != ResultCodeMap.SERVER_RESPONSE_CODE_SUCCESS) {
//                            break;
//                        } else {
//                            if (callback != null) {
//                                UploadNetApiOp.askState(entireMd5);
//                                callback.onSectionCompleted(i + 1, info.total);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            System.gc();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (callback != null) {
//            if (result.resultCode == ResultCodeMap.SERVER_RESPONSE_CODE_SUCCESS) {
//                callback.onUploadCompleted();
//            } else {
//                callback.onUploadFailed(result.resultCode);
//            }
//        }
//        return result;
//    }

    /**
     * 上传子块
     *
     * @param randomAccessFile 资源文件
     * @return
     */
//    private static ServerResult uploadSection(SectionBean section, RandomAccessFile randomAccessFile, int resType) {
//        ByteArrayOutputStream baos = null;
//        try {
//            //完整文件的总字节数
//            long byteCount = randomAccessFile.length();
//            baos = new ByteArrayOutputStream();
//            //该文件分段的实际字节大小
//            int bSize = section.defLenght;
//            if (section.num == (section.total - 1)) {//最后一段，特殊处理（最后一段的大小为剩余的所有字节）
//                bSize = (int) (byteCount - ((section.total - 1) * bSize));
//            }
////            Log.d("cxydebug", "uploadSection: 当前段=" + secIndex + ", 段大小=" + bSize + ", 默认大小=" + secDefSize);
//            //偏移量
//            long offset = section.defLenght * section.num;
//            randomAccessFile.seek(offset);
//            byte[] buffer = new byte[10 * 1024];
//            //分割的总缓存段数
//            int bufferSize = (bSize + buffer.length - 1) / buffer.length;
//
//            for (int j = 0; j < bufferSize; j++) {
//                int rSize = buffer.length;
//                if (j == bufferSize - 1) {//最后一段，字节长度特殊处理
//                    rSize = bSize - (bufferSize - 1) * buffer.length;
//                }
//                randomAccessFile.read(buffer, 0, rSize);
//                baos.write(buffer, 0, rSize);
//            }
//
//            String base64End = Base64.encode(baos.toByteArray());
//            baos.reset();
//            return UploadNetApiOp.upload(base64End, section.num + 1, resType, section.fileId, section.entireMd5, 2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (baos != null) {
//                try {
//                    baos.reset();
//                    baos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return null;
//    }

    /**
     * 上传封面
     *
     * @param name
     * @param md5
     * @param thumb
     * @param desc
     * @param tagIds
     * @param isOpen
     */
//    private static void uploadThumb(String name, String md5, String thumb, String desc, String tagIds, int isOpen) {
//
//        try {
//            RandomAccessFile thumbFile = new RandomAccessFile(thumb, "rw");
//            long byteCount = thumbFile.length();
//            int netType = TelephoneUtil.isWifiEnable(Global.getContext()) ? 1 : 2;
//            int resType = -71001;
//            String format = thumb.substring(thumb.lastIndexOf(".") + 1);
//            if (TextUtils.isEmpty(format) || format.length() < 3 || format.length() > 4) {
//                format = "jpg";
//            }
//            String entireMd5 = DigestUtil.getFileMD5(new File(thumb));
//
//            //检查本地已上传，但中途失败的文件上传记录
//            SectionBean info = null;
//            /**
//             * @param entireMd5 完整文件的MD5
//             * @param name      名称
//             * @param byteCount 完整文件大小
//             * @param netType   当前网络类型
//             * @param resType   资源类型
//             * @param format    文件格式
//             * @param desc      视频描述
//             * @param tagIds    视频关联的标签ID列表
//             * @param isOpen    是否公开
//             * @param auditMode 审核模式。文件上传后的审核模式. 1-编辑审核,2-直接上架. 不传该参数默认为1-编辑审核
//             * @param pMd5      父文件的MD5值
//             */
//            //请求握手信息
//            ServerResult<SectionBean> resAvaliable = UploadNetApiOp.checkAvaliable(entireMd5, name, byteCount, netType, resType, format, desc, tagIds, isOpen, 2, md5);
//            if (resAvaliable != null) {
//                if (resAvaliable.getCsResult().isRequestOK()) {
//                    if (resAvaliable.itemList != null && !resAvaliable.itemList.isEmpty()) {
//                        info = resAvaliable.itemList.get(0);
//                    }
//                }
//            }
//
//            if (info != null) {
//                for (int i = info.num, len = info.total; i < len; i++) {
//                    SectionBean section = new SectionBean();
//                    section.total = info.total;
//                    section.num = i;//从0开始
//                    section.defLenght = info.defLenght;
//                    section.entireMd5 = entireMd5;
//                    section.fileId = info.fileId;
//                    uploadSection(section, thumbFile, resType);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private static int verify(String name, String data, long maxTime) {
//        if (!FileUtil.isFileExits(data)) {
//            return ResultCodeMap.RESULT_CODE_FILE_NOT_FOUND;
//        }
//        File file = new File(data);
//        if (file.length() > 30l * 1024 * 1024) {
//            return ERROR_CODE_FILE_TOO_LARGE;
//        }
//
//        if (TextUtils.isEmpty(name)) {
//            return ERROR_CODE_NAME_EMPTY;
//        }
//
//        VideoPaperBean info = MediaUtil.video2Bean(data);
//
//        if (info == null || TextUtils.isEmpty(info.mimeType) || !info.mimeType.toUpperCase().contains("MP4")) {
//            return ERROR_CODE_MIMETYPE_UNSUPPORTED;
//        }
//
//        //是否为竖屏格式
//        //1、如果和当前宽高一致，那么直接认为是竖屏。否则跳2
//        //2、当宽：高在[0.5621, 0.6250]这个全闭区间内，那么就认为是竖屏
//        //部分机型出现比例略在边界外的情况（0.5666）暂时注释
//        if (info.width != ScreenUtil.getCurrentScreenWidth(Global.getContext())
//                || info.height != ScreenUtil.getCurrentScreenHeight(Global.getContext())) {
//            int ratio = (int) ((info.width / (info.height * 1.0f)) * 10000);
//            if (5500 > ratio || 6700 < ratio) {
//                //非竖屏
//                return ERROR_CODE_IS_NOT_PORT;
//            }
//        }
//
//        if (info.duration >= maxTime) {
//            return ERROR_CODE_DURATION_TOO_LONG;
//        }
//
//        return ResultCodeMap.SERVER_RESPONSE_CODE_SUCCESS;
//    }
}
