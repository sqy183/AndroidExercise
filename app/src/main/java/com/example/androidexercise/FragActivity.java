package com.example.androidexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);

        replaceFrag(new LeftTopFragment());

        findViewById(R.id.button_left).setOnClickListener(this);
        findViewById(R.id.button_right).setOnClickListener(this);

    }

    /**
     * 改变碎片的按键控制
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_left:
                replaceFrag(new LeftTopFragment());
                break;
            case R.id.button_right:
                replaceFrag(new RightTopFragment());
                break;
            default:
                break;
        }
    }

    /**
     * 在FrameLayout（frag_top）中替换碎片
     * @param fragment
     */
    private void replaceFrag(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_top, fragment);

        //将事务添加到返回栈中
        transaction.addToBackStack(null);

        transaction.commit();
    }
}