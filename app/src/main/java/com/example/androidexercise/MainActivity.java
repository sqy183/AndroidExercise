package com.example.androidexercise;
/**
 * 2020.8.6
 * 该界面主要包含功能：菜单显示、监听
 * 可以跳转到其他的功能界面
 * @author sqy183
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 活动的生存周期函数
 * onCreate() //完整生存期
 *     onStart() //可见生存期
 *     //在未被onDestroy()、已onStop()时重新start活动，会先onRestart()，再onStart()
 *         onResume() //前台生存期
 *         onPause()
 *     onStop()
 * onDestroy()
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 活动在被系统回收之前会调用onSaveInstanceState()方法，该方法保存的数据可以在再次创建时获得
     * @param savedInstanceState 一般为null，但 onSaveInstanceState()方法保存的数据在该参数中
     */
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
        findViewById(R.id.sendResult).setOnClickListener(this);

        //接收活动销毁时onSaveInstanceState()保存的数据
//        if(savedInstanceState!=null) {
//            String string=savedInstanceState.getString("key");
//            //...
//        }
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
                Intent intent1=new Intent(MainActivity.this,IntentActivity.class);
                //利用Intent传输数据
                //putExtra()第一个参数是键，由于接收Intent分辨；第二个参数是数据
                intent1.putExtra("tel",tel);
                startActivity(intent1);
                break;
            case R.id.sendResult:
                Intent intent2=new Intent(MainActivity.this,IntentActivity.class);
                //方法startActivityForResult()用于启动活动，但这个方法使得启动的活动在销毁时能够返回一个结果给上一个活动
                //参数：第一个是intent，第二个是请求码
                startActivityForResult(intent2,1);
                break;
            default:
                Toast.makeText(this, "抱歉，该按键尚未定义！", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 使用方法startActivityForResult()启动下一个活动后，下一个活动下回后会调用本活动的本方法
     * 本次练习是在下一个活动中新建Intent传递数据，等到调用本方法时接收该数据
     * @param requestCode 请求码：在调用startActivityForResult()启动下一个活动时也需要传入的参数
     * @param resultCode 返回数据时传入的处理结果
     * @param data 携带返回数据的Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
               if(resultCode==RESULT_OK) {
                   String sendResult=data.getStringExtra("sendResult");
                   ((EditText)findViewById(R.id.send_tel)).setText(sendResult);
               }
               break;
            default:
                break;
        }
    }

    /**
     * 当活动被系统回收时，会调用本方法，可以在其中保存需要的数据。
     * 当系统内存不足时，可能会回收已停止的数据
     * 系统状态变化（如屏幕旋转）时会调用本方法。因为这会调用onDestroy()
     * @param outState 保存的数据在该参数中。onCreate()中的参数类型一样
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String string="内容";
        //添加键值和数据
        outState.putString("key",string);
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