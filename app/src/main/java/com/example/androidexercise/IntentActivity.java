package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        findViewById(R.id.find_ip).setOnClickListener(this);
        findViewById(R.id.find_tel).setOnClickListener(this);

        //利用Intent接收数据
        //利用getIntent()接收启动该活动的Intent
        Intent intent=getIntent();
        //取出符合键的数据
        String tel=intent.getStringExtra("tel");
        ((EditText)findViewById(R.id.Tel)).setText(tel);
    }

    /**
     * 响应按键
     * 按键一：通过Intent发送https信息，唤醒可以接受该信息的其他活动，打开网页
     * 按键二：通过Intent发送tel信息，打开通话界面
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.find_ip:
                //Intent intent1=new Intent("android.intent.action.VIEW");
                //public static final String ACTION_VIEW = "android.intent.action.VIEW";
                Intent intent1=new Intent(Intent.ACTION_VIEW);
                //setData()接收Uri的对象。
                //可以AndroidManifest.xml中对应的activity的<intent-filter>中设置<data>标签，设置接受数据的类型
                //接收Intent的activity必须对应action、category，同时符合<data>
                intent1.setData(Uri.parse(((EditText)findViewById(R.id.IP)).getText().toString()));
                startActivity(intent1);
                break;
            case R.id.find_tel:
                //public static final String ACTION_DIAL = "android.intent.action.DIAL";
                Intent intent2=new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:"+((EditText)findViewById(R.id.Tel)).getText().toString()));
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}