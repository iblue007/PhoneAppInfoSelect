package com.xqx.phoneappinfoselect.net_request.upload;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月18日 18:08.</br>
 * @update: </br>
 */

public class SectionBean {

    /**
     * 当前段序号(从0开始)
     */
    public int num;
    /**
     * 当前段校检合
     */
    public String checksum;
    /**
     * 段字节长度
     */
    public int lenght;

    /**
     * 总段数
     */
    public int total;

    /**
     * 默认分段长度
     */
    public int defLenght;

    /**
     * 全文件MD5
     */
    public String entireMd5;
    /**
     * 同步结束状态的轮询间隔时间（单位：秒）
     */
    public int askInterval;

    /**
     * 文件的ID值
     */
    public String fileId;

    @Override
    public String toString() {
        return "SectionBean{" +
                "checksum='" + checksum + '\'' +
                ", num=" + num +
                ", lenght=" + lenght +
                ", total=" + total +
                ", defLenght=" + defLenght +
                ", entireMd5='" + entireMd5 + '\'' +
                '}';
    }

    public static String format(SectionBean bean) {
        if (bean == null) {
            return "";
        }
        return bean.entireMd5 + "|" + bean.total + "|" + bean.defLenght + "|" + bean.num;
    }

    public static Map<String, SectionBean> parse(String sp) {
        if (TextUtils.isEmpty(sp)) {
            return null;
        }
        String[] cells = sp.split(";");
        Map<String, SectionBean> mapping = new HashMap<String, SectionBean>();
        for (int i = 0, len = cells.length; i < len; i++) {
            try {
                String cellStr = cells[i];
                String[] attr = cellStr.split("\\|");

                if (attr != null && attr.length == 4) {
                    String entireMd5 = attr[0];
                    String total = attr[1];
                    String defLenght = attr[2];
                    String num = attr[3];

                    SectionBean section = new SectionBean();
                    section.entireMd5 = entireMd5;
                    section.total = Integer.valueOf(total);
                    section.defLenght = Integer.valueOf(defLenght);
                    section.num = Integer.valueOf(num);

                    mapping.put(entireMd5, section);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapping;
    }
}
