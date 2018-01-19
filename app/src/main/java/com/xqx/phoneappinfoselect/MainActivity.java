package com.xqx.phoneappinfoselect;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xqx.phoneappinfoselect.net_request.base.ServerResult;
import com.xqx.phoneappinfoselect.net_request.upload.UploadNetApiOp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private ProgressDialog progressDialog;
    public final static int REQUEST_READ_PHONE_STATE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private String release;
    private String model;
    private String brand;
    private String systemProperties;
    private boolean isGetOs = false;//是否获取到os

    // 处理压缩返回信息
    private Handler zipHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle bundle = msg.getData();
            int exitType = bundle.getInt("OPTION_STATUS");
            switch (exitType)
            {
                case ZipTool.EXIST_UNZIPFILE:
                  //  Toast.makeText(getApplicationContext(), "输入的解压路径已经存在于当前目录中", Toast.LENGTH_SHORT).show();
                    break;
                case ZipTool.EXIST_ZIPFILE:
                    //Toast.makeText(getApplicationContext(), "输入的压缩文件已经存在于该目录中", Toast.LENGTH_SHORT).show();
                    break;
                case ZipTool.NOTEXIST_ZIPFILE:
                    //Toast.makeText(getApplicationContext(), "需要解压的文件不存在", Toast.LENGTH_SHORT).show();
                    break;
                case ZipTool.NULL_ZIPPATH:
                    //Toast.makeText(getApplicationContext(), "输入解压文件路径为空", Toast.LENGTH_SHORT).show();
                    break;
                case ZipTool.ZIPOPTION_FAIL:
                    setFeedbackTip("数据压缩失败~");
                    break;
                default:
                    setFeedbackTip("数据压缩成功~");
                    break;
            }
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }
    };

    // 处理压缩返回信息
    private Handler upDataHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle bundle = msg.getData();
            String msgStr = bundle.getString("msgStr");
            if (!TextUtils.isEmpty(msgStr)) {
                setFeedbackTip(msgStr);
            }
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }
    };
    private TextView feedBackTip;
    private TextView phoneInfoTv;
    private float density;
    private String resolution;
    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Global.setContext(this);
            Global.setHandler(new Handler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //写权限的请求   6.0以上手机适配
            int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            }else {
                init();
            }
        }else {
            init();
        }
    }

    private void init(){
        progressDialog = ProgressDialog.show(this, "正在创建和压缩数据~", "稍等，马上就好", true, false);
        progressDialog.show();
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setSelection(1,true);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        phoneInfoTv = (TextView) findViewById(R.id.phone_info_tv);
        feedBackTip = (TextView) findViewById(R.id.feedback_tip);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int screenWidth = mDisplayMetrics.widthPixels;
        int screenHeigh = mDisplayMetrics.heightPixels;
        resolution = screenWidth +"x"+ screenHeigh;
        density = mDisplayMetrics.density;
        brand = Build.BRAND;
        release = Build.VERSION.RELEASE;
        model = Build.MODEL;
        systemProperties = PhonOSUtil.getPhoneOS(brand);
        if(!TextUtils.isEmpty(systemProperties)&&!TextUtils.isEmpty(brand)&&!TextUtils.isEmpty(release)&&!TextUtils.isEmpty(model)){
            isGetOs = true;
            ApkTool.scanLocalInstallAppList(MainActivity.this, MainActivity.this.getPackageManager(), new ApkTool.AppInfoListener() {
                @Override
                public void getAppInfoListener(final List<MyAppInfo> appInfoList) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            phoneInfoTv.setText("分辨率: "+ resolution +"\n"+"密度: "+ density +"\n"+"固件版本: "+release+"\n"+"手机型号: "+ model+"\n"
                                    +"品牌:" + brand+"\n"+"手机Os: " + systemProperties+"\n"+"信息采集个数： "+appInfoList.size());
                            zipFolder();
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        }else {
            isGetOs = false;
            setFeedbackTip("该机型不在适配范围~");
            progressDialog.dismiss();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    upload();
                }
                break;

            case REQUEST_EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    init();
                }
                break;

            default:
                break;
        }
    }

    public void uploadData(View view) {
        try {
            if(!isGetOs){
                setFeedbackTip("该机型不在适配范围~");
                return ;
            }
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                }else {
                    upload();
                }
            }else {
                upload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void upload(){
        progressDialog = ProgressDialog.show(this, "正在操作", "稍等，马上就好", true, false);
        progressDialog.show();
        if(TelephoneUtil.isNetworkAvailable(this)){
            String defaultLayout1 = spinner1.getSelectedItem().toString();
            String defaultLayout2 = spinner2.getSelectedItem().toString();
            final String defaultLaout = defaultLayout1 +"x"+defaultLayout2;
            String defalutUrl = null;
            if(BitmapUtil.getInstance().isExternalStorageWritable()){
                defalutUrl = BitmapUtil.defaultDir;
            }else {
                List<String> extSDCardPath = BitmapUtil.getExtSDCardPathList();
                if(extSDCardPath != null && extSDCardPath.size() > 0){
                    defalutUrl = extSDCardPath.get(0);
                }
            }
            if(TextUtils.isEmpty(defalutUrl)){
                setFeedbackTip("sd卡异常，上传失败");
                return;
            }
            String zipFile = defalutUrl + File.separator + BitmapUtil.targetDirZipName+".zip";
            final File file = new File(zipFile);
            if(file.exists()){
                ThreadUtil.executeMore(new Runnable() {
                    @Override
                    public void run() {
                        String terminalID  = (String) SPUtil.get(getApplicationContext(), "terminalID", "");
                        if(TextUtils.isEmpty(terminalID)){
                            terminalID = UploadNetApiOp.Request4TerminalID(getApplicationContext());
                        }
                        if(!TextUtils.isEmpty(terminalID)){
                            SPUtil.put(getApplicationContext(),"terminalID",terminalID);
                            ServerResult<UploadBean> uploadList = UploadNetApiOp.upload(fileToBase64(file), release, brand,model, systemProperties,resolution,density,defaultLaout,terminalID);
                            if(uploadList != null && uploadList.itemList.size() > 0){
                                UploadBean uploadBean = uploadList.itemList.get(0);
                                String msg = uploadBean.getMsg();
                                if(uploadBean.isSuccess()){
                                    sendMeaage("操作成功！");
                                }else {
                                    sendMeaage(msg);
                                }
                            }else {
                                sendMeaage("");
                            }
                        }else {
                            sendMeaage("");
                        }
                    }
                });
            }else {
                progressDialog.dismiss();
                setFeedbackTip("文件地址不存在，不能上传");
            }
        }else {
            setFeedbackTip("网络异常");
            progressDialog.dismiss();
        }
    }
    /***
     * 压缩文件
     */
    private void zipFolder(){
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileUrl = null;
                    String defalutUrl = null;
                    if(BitmapUtil.getInstance().isExternalStorageWritable()){
                        fileUrl = BitmapUtil.targetDir;
                        defalutUrl = BitmapUtil.defaultDir;
                    }else {
                        List<String> extSDCardPath = BitmapUtil.getExtSDCardPathList();
                        if(extSDCardPath != null && extSDCardPath.size() > 0){
                            fileUrl = extSDCardPath.get(0)+ "/phoneappinfo/";
                            defalutUrl = extSDCardPath.get(0);
                        }
                    }
                    if(!TextUtils.isEmpty(fileUrl) && !TextUtils.isEmpty(defalutUrl)){
                        if((new File(fileUrl)).exists()){
                            ZipFileThread zft = new ZipFileThread();
                            zft.setFile(new File(fileUrl));
                            zft.setHandlePath(defalutUrl + File.separator + BitmapUtil.targetDirZipName);
                            zft.setHandler(zipHandler);
                            zft.setZip(true);
                            zft.start();
                        }
                    }else {
                        setFeedbackTip("sd卡异常，压缩失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /***
     * 发送消息给handler
     */
    private void sendMeaage(String msgStr){
        Message msg = new Message();
        Bundle bundle = new Bundle();
        if(TextUtils.isEmpty(msgStr)){
            bundle.putString("msgStr", "操作失败，请重试");
        }else {
            bundle.putString("msgStr", msgStr);
        }
        msg.setData(bundle);
        upDataHandler.sendMessage(msg);
    }

    /**文件转为byte[]*/
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64;
    }

    private void setFeedbackTip(String tip){
        feedBackTip.setVisibility(View.VISIBLE);
        feedBackTip.setText("操作反馈："+tip);
    }

}
