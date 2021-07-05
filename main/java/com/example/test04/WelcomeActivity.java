package com.example.test04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        init();
    }
    /**
        3秒后自动跳转飞控页面
     **/

    private void init(){
                                                                                            //子线程

        new Thread(new Runnable() {
            @Override
            public void run() {
                int n=3;
                while (n>=0){
                    try {
                        Message msg=Message.obtain();
                        msg.obj=n+"秒..";
                        handler.sendMessage(msg);           //发送信息给handler
                        Thread.sleep(1000);            //暂停1秒
                        n--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
                                                                                                //主线程

    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.obj.equals("0秒..")){                                              //如果为0秒，跳转到飞控页面
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{                                                                      //否则就显示当前秒数
                TextView tv=findViewById(R.id.textView);
                tv.setText((String)msg.obj);
            }
        }
    };
}