package com.example.myapplication;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private TextView tv_message;
    private Button bt_message;
    private Button bt_subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message=(TextView)this.findViewById(R.id.tv_message);
        tv_message.setText("MainActivity");
        bt_subscription=(Button)this.findViewById(R.id.bt_subscription);
        bt_subscription.setText("订阅事件");
        bt_message=(Button)this.findViewById(R.id.bt_message);
        bt_message.setText("跳转到SecondActivity");
        bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
        bt_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册事件
                if(!EventBus.getDefault().isRegistered(MainActivity.this)) {
                    EventBus.getDefault().register(MainActivity.this);
                }else{
                    Toast.makeText(MainActivity.this,"请勿重复注册事件",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEven(MessageEvent messageEvent){
        tv_message.setText(messageEvent.getMessage());
    }
    @Subscribe(sticky = true)
    public void ononMoonStickyEven(MessageEvent messageEvent){
        tv_message.setText(messageEvent.getMessage());
    }
}
