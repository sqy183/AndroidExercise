package com.example.androidexercise;
/**
 * 2020.8.6
 * 该界面主要包含功能：菜单显示、监听
 * 可以跳转到其他的功能界面
 * @author sqy183
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用匿名内部类监听按键
/*        findViewById(R.id.drop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        //实现监听接口的内部类监听按键
        //findViewById(R.id.drop).setOnClickListener(new MyButtonListener());
        //通过接口实现监听，必须对按键设置监听
        findViewById(R.id.drop).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
    }

    /**
     * 显示菜单
     * @param menu
     * @return 返回true，菜单才被允许显示
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 定义菜单监听响应事件
     * 包含了显示、隐式Intent的练习
     * @param item
     * @return 返回true，生效
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.intent:
                Toast.makeText(this, "正在进入Intent练习界面", Toast.LENGTH_SHORT).show();
                //显式Intent
                //Intent intent=new Intent(MainActivity.this,FragActivity.class);
                //
                //隐式Intent实现活动跳转。
                //在AndroidManifested.xml中的对应活动下定义<intent-filter>
                //只有当intent中的action、category与<intent-filter>中的<action>、<category>完全匹配时，才能响应
                //"android.intent.category.DEFAULT"是默认category，调用startActivity()时会自动添加到Intent
                //每个Intent只能指定一个action，但是能指定多个category
                Intent intent=new Intent("com.example.activitytext.ACTION_START");
                intent.addCategory("com.example.activitytext.MY_CATEGORY");
                startActivity(intent);
                break;
            /*case R.id.db:
                Toast.makeText(this, "正在进入数据库练习界面", Toast.LENGTH_SHORT).show();
                break;*/
            default:
                Toast.makeText(this, "抱歉，该模块尚未完成", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 通过对MainActivity实现接口设置按键监听
     * 必须在onCreate()中定义监听
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.drop:
                finish();
                Toast.makeText(this, "您已成功退出程序", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send:
                String tel=((EditText)findViewById(R.id.send_tel)).getText().toString();
                Intent intent=new Intent(MainActivity.this,IntentActivity.class);
                //利用Intent传输数据
                //putExtra()第一个参数是键，由于接收Intent分辨；第二个参数是数据
                intent.putExtra("tel",tel);
                startActivity(intent);
            default:
                Toast.makeText(this, "抱歉，该按键尚未定义！", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /*
     * 直接在MainActivity类中定义一个实现了OnClickListener接口的内部类，
     * 在该内部类中实现onClick()方法。
     * 然后在setOnClickListener()函数中new该类。
     *
     * 相比于内部匿名类，实现接口的内部类这种方式，
     * 在按键多时，可重用性更高，在按键较少时，比较麻烦
     */
/*    public class MyButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.drop:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }*/

}