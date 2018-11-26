package com.doing.qinhp.testxf;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.doing.qinhp.testxf.basic.IatBasicActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends IatBasicActivity implements View.OnClickListener {

    //dfdddfg
    private EditText mContent;
    private Button mBtnVoice;
    private Button btIntent;
    private Button bt_appStore;
    private Spinner spinner;

    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    /**
     * 初始化视图
     */
    private void initView() {
        mContent = findViewById(R.id.et_content);
        mBtnVoice = findViewById(R.id.btn_voice);
        btIntent = findViewById(R.id.bt_intent);
        bt_appStore = findViewById(R.id.bt_appStore);

        spinner = findViewById(R.id.sp_list);
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        mBtnVoice.setOnClickListener(this);
        btIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scheme协议
//                另一个APP 的manifest清单文件写法
//                <activity
//                android:
//                name = ".ui.activity.ZMCertTestActivity"
//                android:
//                label = "@string/app_name"
//                android:
//                launchMode = "singleTask"
//                android:
//                screenOrientation = "portrait" >

                //重点
//                <intent - filter >
//                    <action android:name = "android.intent.action.VIEW" / >
//                    <category android:name = "android.intent.category.DEFAULT" / >
//                    <category android:name = "android.intent.category.BROWSABLE" / >
//                    <data

//                                和下面的URI对应
//                            android:scheme = "webcallapp"
//                            android: host = "yceshop.com"
//                            android: path = "/apb0303001Activity"
//                            android:port = "8083" / >
//                </intent - filter >
//                </activity >

                String uri = "webcallapp://yceshop.com:8083/apb0303001Activity?itemId=528";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
        bt_appStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开Android应用市场
                String uri2 = "market://details?id=com.yceshop";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri2)));
            }
        });
        //调用父类方法
        initIatData(mContent);

        //数据
        data_list = new ArrayList<>();
        data_list.add("所有");
        data_list.add("业务员A");
        data_list.add("管理员A");
        data_list.add("业务员B");
        data_list.add("管理员B");

        //适配器
        arr_adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

    }

    Spinner.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this, data_list.get(position), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(MainActivity.this, "没有选择", Toast.LENGTH_SHORT).show();
        }
    }
    ;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_voice:
                //权限申请
                requestPower();
                //调用父类方法
//                AndPermission.with(MainActivity.this).requestCode(100).permission(Manifest.permission.RECORD_AUDIO).callback(this).start();
//                clickMethod();
                break;
        }

    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//            //如果应用之前请求过此权限，但是用户拒绝了请求，此方法放回true
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.RECORD_AUDIO)){
//                //写对话框，为什么申请权限，并在对话框的确认键后续再次申请权限
//                Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();
//            }else {
            //申请权限，字符串数组内是一个或多个要申请的权限，
            // 1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,}, 1);
//            }
        } else {
            clickMethod();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    clickMethod();
                } else {
                    Toast.makeText(this, "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

