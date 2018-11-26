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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doing.qinhp.testxf.basic.IatBasicActivity;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity  extends IatBasicActivity implements View.OnClickListener {

    //dfdddfg
    private EditText mContent;
    private Button mBtnVoice;
    private Button btIntent;
    private Button bt_appStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    /**
     * 初始化视图
     */
    private void initView(){
        mContent = findViewById(R.id.et_content);
        mBtnVoice =findViewById(R.id.btn_voice);
        btIntent = findViewById(R.id.bt_intent);
        bt_appStore = findViewById(R.id.bt_appStore);
        mBtnVoice.setOnClickListener(this);
        btIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "webcallapp://yceshop.com:8083/apb0303001Activity?itemId=528";
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(uri)));
            }
        });
        bt_appStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri2 = "market://details?id=com.yceshop";
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(uri2)));
            }
        });
        //调用父类方法
        initIatData(mContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_voice:
                //权限申请
                requestPower();
                //调用父类方法
//                AndPermission.with(MainActivity.this).requestCode(100).permission(Manifest.permission.RECORD_AUDIO).callback(this).start();
//                clickMethod();
                break;
        }

    }

    public void requestPower(){
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
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
        }else {
            clickMethod();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==1){
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i]==PERMISSION_GRANTED) {
                    clickMethod();
                }else {
                    Toast.makeText(this, "权限"+permissions[i]+"申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

