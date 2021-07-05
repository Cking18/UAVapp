package com.zhao.uav.control;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.example.test04.R;
import com.zhao.uav.scoket.UAVScoket;

public class RightUIControl {
    private FragmentActivity activity;

    public RightUIControl(FragmentActivity activity){
        this.activity=activity;
    }
                                                             /**右边UI操作**/
    public void rightUI(){
        //点击向左旋转按钮
        LinearLayout leftleft=activity.findViewById(R.id.leftleft);
        leftleft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    leftleft.setBackgroundColor(Color.argb(50,220,0,0));
                    UAVScoket.uavLeftLeft();
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    leftleft.setBackgroundColor(Color.argb(0,220,0,0));
                    UAVScoket.uavHX();
                }
                return true;
            }
        });
        //点击向右旋转按钮
        LinearLayout rightright=activity.findViewById(R.id.rightright);
        rightright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    rightright.setBackgroundColor(Color.argb(50,220,0,0));
                    UAVScoket.uavRightRight();
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    rightright.setBackgroundColor(Color.argb(0,220,0,0));
                    UAVScoket.uavHX();
                }
                return true;
            }
        });
        //点击向上飞
        LinearLayout up=activity.findViewById(R.id.up);
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    up.setBackgroundColor(Color.argb(50,220,0,0));
                    //加油
                    int x34 = UAVScoket.addPower();
                    BottomUIControl.speed.setText("速度  "+x34+"m/s");
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    up.setBackgroundColor(Color.argb(0,220,0,0));
                }
                return true;
            }
        });
        //点击向下飞
        LinearLayout down=activity.findViewById(R.id.down);
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=event.getAction();                              //获取触摸状态值
                if(x==MotionEvent.ACTION_DOWN){                       //如果是按下状态
                    down.setBackgroundColor(Color.argb(50,220,0,0));
                    //减油
                    int x34 = UAVScoket.lessPower();
                    BottomUIControl.speed.setText("速度  "+x34+"m/s");
                }
                else if(x==MotionEvent.ACTION_UP){                   //如果是松开状态
                    down.setBackgroundColor(Color.argb(0,220,0,0));
                }
                return true;
            }
        });
    }
}
