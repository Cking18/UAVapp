package com.zhao.uav.control;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.test04.R;
import com.zhao.uav.scoket.UAVScoket;

public class BottomUIControl {

    private FragmentActivity activity;
    private boolean backflag=false;                                   //标记是否返回
    private boolean flyflag=false;                                    //标记是否启动
    public static TextView speed;                                    //定义显示控件
    public boolean updownflag;
    public boolean roker;


    /**
     *调用顶部UI操作类
     * @param activity 传入MainActivity
     **/
    public BottomUIControl(FragmentActivity activity){
        this.activity=activity;
        speed=activity.findViewById(R.id.speed);                    //获取速度控件
    }

    /**
     *底部UI操作
     * **/
    public void bottomUI(){
        //点击返回按钮
        ImageView back=activity.findViewById(R.id.fan);

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();
                if (x == MotionEvent.ACTION_DOWN) {
                    back.setImageResource(R.mipmap.fan1);
                    back.setBackgroundColor(Color.argb(50,220,0,0));
                    if (!updownflag) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int x34=UAVScoket.getYM();
                                while (x34 > 0) {
                                    updownflag=true;
                                    try {
                                        x34-=20;
                                        UAVScoket.setYM(x34);
                                        Message msg = Message.obtain();
                                        msg.obj=x34;
                                        handler.sendMessage(msg);
                                        Thread.sleep(60);
                                    }catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }
                                updownflag=false;
                            }
                        }).start();
                    }
                }
                else if (x==MotionEvent.ACTION_UP){
                    back.setImageResource(R.mipmap.fan1);
                    back.setBackgroundColor(Color.argb(0,220,0,0));
                }
                return false;
            }
        });
        //点击起飞按钮
        ImageView fly=activity.findViewById(R.id.fei);
        fly.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = event.getAction();
                if (x == MotionEvent.ACTION_DOWN) {
                    fly.setImageResource(R.mipmap.fei1);
                    fly.setBackgroundColor(Color.argb(50,220,0,0));
                    if (!updownflag) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int x34 = UAVScoket.getYM();
                                while (x34<600){
                                    updownflag=true;
                                    try {
                                        x34+=20;
                                        UAVScoket.setYM(x34);
                                        Message msg = Message.obtain();
                                        msg.obj=x34;
                                        handler.sendMessage(msg);
                                        Thread.sleep(60);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                updownflag=false;
                            }
                        }).start();
                    }
                } else if (x == MotionEvent.ACTION_UP) {
                    fly.setImageResource(R.mipmap.fei1);
                    fly.setBackgroundColor(Color.argb(0,220,0,0));
                }
                return false;
            }
        });

        RockerView rockerView=activity.findViewById(R.id.rocker);
        rockerView.setOnShakeListener(RockerView.DirectionMode.DIRECTION_8, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void direction(RockerView.Direction direction) {
                if(direction== RockerView.Direction.DIRECTION_CENTER){
                    UAVScoket.uavHG();
                    UAVScoket.uavFY();
                    UAVScoket.uavHX();
                }
                else if(direction== RockerView.Direction.DIRECTION_LEFT){
                    UAVScoket.uavLeft();
                }else if (direction == RockerView.Direction.DIRECTION_RIGHT){
                    UAVScoket.uavRight();
                }else if(direction == RockerView.Direction.DIRECTION_UP){
                    UAVScoket.uavFront();
                } else if (direction == RockerView.Direction.DIRECTION_DOWN) {
                    UAVScoket.uavAfter();
                } else if (direction == RockerView.Direction.DIRECTION_UP_LEFT) {
                    UAVScoket.uavFront();
                    UAVScoket.uavLeft();
                } else if (direction == RockerView.Direction.DIRECTION_UP_RIGHT) {
                    UAVScoket.uavFront();
                    UAVScoket.uavRight();
                } else if (direction == RockerView.Direction.DIRECTION_DOWN_LEFT) {
                    UAVScoket.uavLeft();
                    UAVScoket.uavAfter();
                } else if (direction == RockerView.Direction.DIRECTION_DOWN_RIGHT) {
                    UAVScoket.uavAfter();
                    UAVScoket.uavRight();
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }
        Handler handler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            speed.setText("速度    "+msg.obj+"m/s");
        }
        };


}
